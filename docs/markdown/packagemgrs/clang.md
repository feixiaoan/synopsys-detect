# C/C++ (Clang) support

C/C++ (Clang) support is limited to Linux systems that support one of the following
package manager commands: APK, DPKG, or RPM.

For C/C++ projects on Linux systems that meet these requirements, [solution_name] can derive
dependency information using information read from a JSON Compilation Database
(compile_commands.json file) and the Linux package manager.
[solution_name] will only find components that have been installed into the Linux package manager database.

The JSON compilation database
must be generated by your project build before running [solution_name].
There are multiple C/C++ build tools that are capable of generating a JSON compilation database.
For example, some versions of CMake generate a JSON compilation database
when run with the following option:

-DCMAKE_EXPORT_COMPILE_COMMANDS=ON

The Clang detector runs when it finds a compile_commands.json file
in the project directory. If the compile_commands.json file resides in a sub-directory,
adjust the [detector search depth](../properties/configuration/paths.md#detector-search-depth)
to enable [solution_name] to find it.
Make sure the directory in which the compile_commands.json is located is not
being [excluded from detector search](../downloadingandrunning/includingexcluding.md#directory-exclusions).

For each compile command in the compile_commands.json file, the Clang detector
runs a version of the command that is (slightly) modified to ensure that it does
not overwrite build artifacts, and generate a list of dependency files used.
This is performed by adding the *-M* and *-MF* compiler options.
It then uses the Linux package manager to identify which installed package owns each
dependency file. These packages are added as a component to the results.

Any dependency file that is not recognized by the Linux package manager
and resides outside the source directory (the directory containing the
compile_commands.json file) is written to the status.json file under
unrecognizedPaths.CLANG.
