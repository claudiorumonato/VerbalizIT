# VerbalizIT
Extract the audio from a video and transcript the contents to a file.

# Install

Download the language model from https://alphacephei.com/vosk/models and unpack it under src/main/resources/vosk.

Copy ffmpeg-amd64.exe under C:\<USER>\AppData\Local\Temp\jave\ffmpeg-amd64-3.5.0.exe.

# Usage

Invoke the program passing the path to the input video.

Optionally, you can specify the filename for generated audio e text files.

If omitted, audio and text files will have the same name of the input file, with .wav and .txt extension respectively.

If the audio file exists it will be used instead of extracting it again from the video.
