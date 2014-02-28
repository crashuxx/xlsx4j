xlsx4j
======

Fast and lightweight implementation of xlsx file reader written in java

Benchamrk
=========

small file (100rows 3 columns)
```
 testFileIteration hasNext 32 ms
 testFileIteration foreach 23 ms
 testFileIteration All 87 ms
 
 testStreamLazyIteration hasNext 102 ms
 testStreamLazyIteration foreach 11 ms
 testStreamLazyIteration All 114 ms
 
 testStreamIteration hasNext 24 ms
 testStreamIteration foreach 4 ms
 testStreamIteration All 30 ms
```
 
big file (100000rows 2columns)
```
 testFileIteration hasNext 4180 ms
 testFileIteration foreach 11306 ms
 testFileIteration All 15519 ms
 
 testStreamLazyIteration hasNext 12289 ms
 testStreamLazyIteration foreach 13364 ms
 testStreamLazyIteration All 25655 ms
 
 testStreamIteration hasNext 15602 ms
 testStreamIteration foreach 9930 ms
 testStreamIteration All 25533 ms
```
