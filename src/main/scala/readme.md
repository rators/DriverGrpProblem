# DNA Assembly Solution Walk-through
-----

## Longest Overlapping Substring
-----
**Problem**: Given two string **A** and **B** find the largest overlapping substring that is the prefix 
of one string as well as the suffix of the other.

*e.g* A: **HE**LLO and B: CAC**HE** share string **HE** of length 2. **HE** is the prefix of A and the 
suffix of B.

In order to combine these two strings (A and B) I needed to know which string is the prefix string and what 
the size of the longest overlapping substring. The bulk of the solution is contained in the `getOverlap` function.

Function `getOverlap` does the following: given strings `strOne` and `strTwo`, return the length of their overlap and the position of 
`strOne` in the **shortest common super-sequence** of A and B.

## Shorest Common Super-sequence [SCS]
-----
Given a list of strings **L** find the shortest common super-sequence (SCS). The SCS is the shortest
string that contains all the strings in **L** as substrings.
 
### Graph Reduction
Using the function for the **longest overlapping substring** for determining edge weights,
a uni-directional graph is constructed. The edge weights are set to the length of the longest common 
overlapping sub-string of the **source** and **destination** nodes in the edge.

**Edge directions represent the following**

- `source`: *prefix* of the **SCS**
- `destination`: *suffix* of the **SCS**
 
#### Reduction Operation
- For each edge remove the edge from the list of edges if it has a destination node.
  - Set the string values of both nodes to the SCS between the two.

**Rule[1]**: Each string has one other string with which it shares more than half it's length of overlap.

Due to Rule[1] we can be sure that we should end up with an edge with no destination node at the end of our reduction.



