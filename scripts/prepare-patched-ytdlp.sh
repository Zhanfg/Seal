#!/bin/sh
# Build the patched yt-dlp Python zipapp for the Android application resource overlay.
# No arguments and no credentials required.
set -eu

ROOT=$(CDPATH= cd -- "$(dirname -- "$0")/.." && pwd)
REVISION=f74b68246987604aa2344eb26745201d063a6dc2
REPOSITORY=https://github.com/Zhanfg/yt-dlp.git
DESTINATION="$ROOT/app/src/main/res/raw/ytdlp"
WORK=$(mktemp -d)

trap 'rm -rf "$WORK"' EXIT HUP INT TERM

git -C "$WORK" init -q src
git -C "$WORK/src" remote add origin "$REPOSITORY"
git -C "$WORK/src" fetch -q --depth 1 origin "$REVISION"
git -C "$WORK/src" checkout -q --detach FETCH_HEAD

ACTUAL=$(git -C "$WORK/src" rev-parse HEAD)
if [ "$ACTUAL" != "$REVISION" ]; then
    echo "Unexpected yt-dlp commit: $ACTUAL" >&2
    exit 1
fi

make -C "$WORK/src" yt-dlp
python3 "$WORK/src/yt-dlp" --version

mkdir -p "$(dirname "$DESTINATION")"
cp "$WORK/src/yt-dlp" "$DESTINATION"
test -s "$DESTINATION"
echo "Embedded yt-dlp revision: $REVISION"
