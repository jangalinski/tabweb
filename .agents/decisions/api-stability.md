# Experimental API Stability

Tabweb is experimental throughout the `0.0.x` release line. During this phase,
breaking API changes are allowed and existing consumer compatibility must not
constrain design or implementation decisions.

Compatibility becomes a design requirement only when the project reaches a
minor `0.1.x` release. Until then, prefer the clearest and most coherent API,
even when that requires removing, renaming, or restructuring existing public
declarations.
