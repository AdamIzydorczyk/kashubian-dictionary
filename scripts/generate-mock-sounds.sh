#!/usr/bin/env bash
# Regenerates the sound files seeded from /seed/kashubian-words.json (the "sound"
# key of a seed word). They are synthetic three-tone sequences, on purpose: no
# recordings of these Kashubian words are published on pl.wiktionary.org or
# Wikimedia Commons, and a tone cannot be mistaken for a pronunciation.
# The generated files are committed, so running this is only needed to change them.
#
# Requires ffmpeg. Files are deterministic: the tones come from the word itself.
set -euo pipefail

REPO_ROOT="$(cd "$(dirname "${BASH_SOURCE[0]}")/.." && pwd)"
OUT_DIR="$REPO_ROOT/src/main/resources/seed/sounds"
WORDS=(bialka chlop dzecko matka woda jazek miesac spiewac)

mkdir -p "$OUT_DIR"
python3 - "$OUT_DIR" "${WORDS[@]}" <<'PY'
import hashlib
import os
import subprocess
import sys

out_dir, words = sys.argv[1], sys.argv[2:]
duration, fade_in, fade_out = 0.35, 0.02, 0.03

for word in words:
    digest = int(hashlib.md5(word.encode()).hexdigest(), 16)
    frequencies = [220 + (digest >> (8 * i)) % 480 for i in range(3)]
    total = duration * len(frequencies)
    command = ["ffmpeg", "-v", "error", "-y"]
    for frequency in frequencies:
        command += ["-f", "lavfi", "-i", f"sine=frequency={frequency}:duration={duration}"]
    command += [
        "-filter_complex",
        (f"[0:a][1:a][2:a]concat=n={len(frequencies)}:v=0:a=1,"
         f"afade=t=in:st=0:d={fade_in},"
         f"afade=t=out:st={total - fade_out:.2f}:d={fade_out}"),
        "-codec:a", "libmp3lame", "-q:a", "9",
        os.path.join(out_dir, f"mock-{word}.mp3"),
    ]
    subprocess.run(command, check=True)
    print(f"mock-{word}.mp3 {frequencies} Hz")
PY
