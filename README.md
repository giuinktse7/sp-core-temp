# Sequenceplanner Core

### Troubleshooting
#### IO error while decoding [...].scala with UTF-8
The scala compiler and the JVM might be using conflicting file encodings. Try adding
```
JAVA_TOOL_OPTIONS="-Dfile.encoding=UTF8"
```
to your environment variables.
