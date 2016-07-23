# String Assembly Solution Walk-through
-----
## Intro
Given a set of DNA fragments re-assemble them into one DNA sequence by
exploiting the property that each fragment overlaps with one other fragment by more than half.

Solution

- [Find Links](#find_links): For every string S find another string in the list whose prefix overlaps with S by half or more of its length (the **LCS**).
- [Create Set of Links](#create_links). Each source node in a link overlaps with it's neighbors by more than half.
- [Reduce](#reduce_links): Reduce the links into one assembled dna sequence string.

## Find Links: Longest Overlapping Substring <a name="find_links"></a>
-----
**Problem**: Given two strings **A** and **B** find the largest substring that is both a prefix of one and also a suffix of the other.

For example, string A '**HE**LLO' and string B 'CAC**HE**' share string **HE** of length 2. **HE** is the prefix of A and the suffix of B.

In order to combine these two strings (A and B) I needed to know which string is the prefix string and what 
the size of the longest overlapping substring. The bulk of the solution is contained in the `getOverlap` function.

Function `getOverlap` does the following: given strings `strOne` and `strTwo`, return the length of their overlap and the position of 
`strOne` in the **shortest common super-sequence** of A and B.

## Create Links [SCS]<a name="create_links"></a>
-----
Create a list of links with `source` and `destination` nodes. 

**Link directions represent the following**

- `source`: *prefix* of the **SCS** with `destination`
- `destination`: *suffix* of the **SCS** with `source`
 
Link length values are equal to the length of the longest common overlapping sub-string of the **source** and **destination** nodes in the link using `getOverlap`.

## Reduce Links<a name="reduce_links"></a>
-----
- For each link L remove L from the list of links if it has a destination node.
  - Drop N (value of L) characters from the right of `start` string.     - Append `destination` string to `start` string creating a new string `DS`.
  - Point `destination` and `start` string to 'DS'

- The final link remaining should contain a sequence that contains all the strings in L as substrings.

