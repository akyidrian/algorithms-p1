See the Assessment Guide for information on how to interpret this report.

ASSESSMENT SUMMARY

Compilation:  PASSED
API:          PASSED

Findbugs:     FAILED (2 warnings)
Checkstyle:   FAILED (1 warning)

Correctness:  27/27 tests passed
Memory:       8/8 tests passed
Timing:       42/42 tests passed

Aggregate score: 100.00%
[Compilation: 5%, API: 5%, Findbugs: 0%, Checkstyle: 0%, Correctness: 60%, Memory: 10%, Timing: 20%]

ASSESSMENT DETAILS

The following files were submitted:
----------------------------------
 12K Jun  4 13:15 KdTree.java
4.1K Jun  4 13:15 PointSET.java


********************************************************************************
*  COMPILING                                                                    
********************************************************************************


% javac PointSET.java
*-----------------------------------------------------------

% javac KdTree.java
*-----------------------------------------------------------


================================================================


Checking the APIs of your programs.
*-----------------------------------------------------------
PointSET:

KdTree:

================================================================


********************************************************************************
*  CHECKING STYLE AND COMMON BUG PATTERNS                                       
********************************************************************************


% findbugs *.class
*-----------------------------------------------------------
L P UPM_UNCALLED_PRIVATE_METHOD UPM: The private method 'isHorizontal()' is never called.  At KdTree.java:[line 124]
M D IM_BAD_CHECK_FOR_ODD IM: Uses an expression like 'x % 2 == 1' to check whether an integer is odd, but this won't work for negative integers. Instead, use an expression like 'x % 2 != 0'.  At KdTree.java:[line 124]
Warnings generated: 2

================================================================


% checkstyle *.java
*-----------------------------------------------------------
KdTree.java:326:21: '{' is not preceded with whitespace. [WhitespaceAround]
Checkstyle ends with 1 error.

================================================================


********************************************************************************
*  TESTING CORRECTNESS
********************************************************************************

Testing correctness of PointSET
*-----------------------------------------------------------
Running 8 total tests.

A point in an m-by-m grid means that it is of the form (i/m, j/m),
where i and j are integers between 0 and m

Test 1: Test size() by inserting n random points
        (size may be less than n because of duplicates)
  * 100000 random points in a 100000-by-100000 grid
  * 100000 random points in a 10000-by-10000 grid
  * 100000 random points in a 1000-by-1000 grid
  * 100000 random points in a 100-by-100 grid
  * 100000 random points in a 10-by-10 grid
==> passed

Test 2: Test isEmpty() by checking for n = 0, 1, and 2 points
  * 0 points
  * 1 point
  * 2 points
==> passed

Test 3: Insert n random points and check contains() for random query points
  * 100000 random points in a 100000-by-100000 grid
  * 100000 random points in a 10000-by-10000 grid
  * 100000 random points in a 1000-by-1000 grid
  * 100000 random points in a 100-by-100 grid
  * 100000 random points in a 10-by-10 grid
==> passed

Test 4: Insert n random points and check nearest() for random query points
  * 1000 random points in a 100000-by-100000 grid
  * 1000 random points in a 10000-by-10000 grid
  * 1000 random points in a 1000-by-1000 grid
  * 1000 random points in a 100-by-100 grid
  * 1000 random points in a 10-by-10 grid
==> passed

Test 5: Insert n random points and check range() for random query rectangles
  * 1000 random rectangles and points in a 100000-by-100000 grid
  * 1000 random rectangles and points in a 10000-by-10000 grid
  * 1000 random rectangles and points in a 1000-by-1000 grid
  * 1000 random rectangles and points in a 100-by-100 grid
  * 1000 random rectangles and points in a 10-by-10 grid
==> passed

Test 6: Intermixed sequence of calls to isEmpty(), size(), insert(),
        contains(), range(), and nearest() with probabilities
        p1, p2, p3, p4, p5, and p6, respectively
  * 10000 calls with random points in a 10000-by-10000 grid
    and probabilities 0.05, 0.05, 0.3, 0.2, 0.2, 0.2
  * 10000 calls with random points in a 1000-by-1000 grid
    and probabilities 0.05, 0.05, 0.3, 0.2, 0.2, 0.2
  * 10000 calls with random points in a 100-by-100 grid
    and probabilities 0.05, 0.05, 0.3, 0.2, 0.2, 0.2
  * 10000 calls with random points in a 10-by-10 grid
    and probabilities 0.05, 0.05, 0.3, 0.2, 0.2, 0.2
  * 10000 calls with random points in a 1-by-1 grid
    and probabilities 0.05, 0.05, 0.3, 0.2, 0.2, 0.2
==> passed

Test 7: Intermixed sequence of calls to isEmpty(), size(), insert(),
        contains(), range(), and nearest() with probabilities
        p1, p2, p3=0, p4, p5, and p6, respectively
        (data structure with 0 points)
  * 1000 calls with random points in a 1000-by-1000 grid
    and probabilities 0.5, 0.5, 0.0, 0.0, 0.0, 0.0
  * 1000 calls with random points in a 1000-by-1000 grid
    and probabilities 0.2, 0.2, 0.0, 0.6, 0.0, 0.0
  * 1000 calls with random points in a 1000-by-1000 grid
    and probabilities 0.2, 0.2, 0.0, 0.0, 0.6, 0.0
  * 1000 calls with random points in a 1000-by-1000 grid
    and probabilities 0.2, 0.2, 0.0, 0.0, 0.0, 0.6
  * 1000 calls with random points in a 1000-by-1000 grid
    and probabilities 0.2, 0.2, 0.0, 0.2, 0.2, 0.2
==> passed

Test 8: Test whether two PointSET objects can be created at the same time
==> passed


Total: 8/8 tests passed!


================================================================
Testing correctness of KdTree
*-----------------------------------------------------------
In the tests below, we consider three classes of points and rectangles.

  * Non-degenerate points: no two points (or rectangles) share either an
                           x-coordinate or a y-coordinate

  * Distinct points:       no two points (or rectangles) share both an
                           x-coordinate and a y-coordinate

  * General points:        no restrictions on the x-coordinates or y-coordinates
                           of the points (or rectangles)

A point in an GRID-by-GRID grid means that it is of the form (i/GRID, j/GRID),
where i and j are integers between 0 and GRID

Running 19 total tests.

Test 1a: Insert n non-degenerate points and check size() after each insertion
  * 50000 random non-degenerate points in a 100000-by-100000 grid
  * 5000 random non-degenerate points in a 10000-by-10000 grid
  * 500 random non-degenerate points in a 1000-by-1000 grid
  * 50 random non-degenerate points in a 100-by-100 grid
  * 10 random non-degenerate points in a 10-by-10 grid
  * 1 random non-degenerate points in a 1-by-1 grid
==> passed

Test 1b: Insert n distinct points and check size() after each insertion
  * 100000 random distinct points in a 100000-by-100000 grid
  * 100000 random distinct points in a 10000-by-10000 grid
  * 100000 random distinct points in a 1000-by-1000 grid
  * 10000 random distinct points in a 100-by-100 grid
  * 100 random distinct points in a 10-by-10 grid
  * 1 random distinct points in a 1-by-1 grid
==> passed

Test 1c: Insert n general points and check size() after each insertion
  * 100000 random general points in a 100000-by-100000 grid
  * 100000 random general points in a 10000-by-10000 grid
  * 100000 random general points in a 1000-by-1000 grid
  * 100000 random general points in a 100-by-100 grid
  * 100000 random general points in a 10-by-10 grid
  * 10 random general points in a 1-by-1 grid
==> passed

Test 2: Test size() and isEmpty() for n = 0, 1, and 2 points
  * 0 points
  * 1 point
  * 2 points
==> passed

Test 3a: Insert n non-degenerate points and call contains() with random query points
  * 50000 random non-degenerate points in a 100000-by-100000 grid
  * 5000 random non-degenerate points in a 10000-by-10000 grid
  * 500 random non-degenerate points in a 1000-by-1000 grid
  * 50 random non-degenerate points in a 100-by-100 grid
  * 5 random non-degenerate points in a 10-by-10 grid
  * 1 random non-degenerate points in a 1-by-1 grid
==> passed

Test 3b: Insert n distinct points and call contains() with random query points
  * 100000 random distinct points in a 100000-by-100000 grid
  * 100000 random distinct points in a 10000-by-10000 grid
  * 100000 random distinct points in a 1000-by-1000 grid
  * 10000 random distinct points in a 100-by-100 grid
  * 100 random distinct points in a 10-by-10 grid
  * 1 random distinct points in a 1-by-1 grid
==> passed

Test 3c: Insert n general points and call contains() with random query points
  * 10000 random general points in a 1000-by-1000 grid
  * 10000 random general points in a 100-by-100 grid
  * 10000 random general points in a 10-by-10 grid
  * 10000 random general points in a 1-by-1 grid
==> passed

Test 4: Test whether two KdTree objects can be created at the same time
==> passed

Test 5a: Insert n non-degenerate points and call range() for n random query rectangles
  * 5000 random non-degenerate points and rectangles in a 100000-by-100000 grid
  * 5000 random non-degenerate points and rectangles in a 10000-by-10000 grid
  * 500 random non-degenerate points and rectangles in a 1000-by-1000 grid
  * 50 random non-degenerate points and rectangles in a 100-by-100 grid
  * 5 random non-degenerate points and rectangles in a 10-by-10 grid
==> passed

Test 5b: Insert n distinct points and call range() for n random query rectangles
  * 5000 random distinct points and rectangles in a 100000-by-100000 grid
  * 5000 random distinct points and rectangles in a 10000-by-10000 grid
  * 1000 random distinct points and rectangles in a 1000-by-1000 grid
  * 1000 random distinct points and rectangles in a 100-by-100 grid
  * 5 random distinct points and rectangles in a 10-by-10 grid
==> passed

Test 5c: Insert n general points and call range() for n random query rectangles
  * 5000 random general points and rectangles in a 10000-by-10000 grid
  * 5000 random general points and rectangles in a 1000-by-1000 grid
  * 5000 random general points and rectangles in a 100-by-100 grid
  * 5000 random general points and rectangles in a 10-by-10 grid
  * 5000 random general points and rectangles in a 1-by-1 grid
==> passed

Test 5d: Insert n points and call range() for tiny rectangles enclosing each point.
  * 4000 tiny rectangles and 4000 points in a 100000-by-100000 grid
  * 4000 tiny rectangles and 4000 points in a 10000-by-10000 grid
  * 4000 tiny rectangles and 4000 points in a 1000-by-1000 grid
  * 4000 tiny rectangles and 4000 points in a 100-by-100 grid
  * 4000 tiny rectangles and 4000 points in a 10-by-10 grid
==> passed

Test 6a: Insert n non-degenerate points and call nearest() with random query points
  * 50000 random non-degenerate points in a 100000-by-100000 grid
  * 5000 random non-degenerate points in a 10000-by-10000 grid
  * 500 random non-degenerate points in a 1000-by-1000 grid
  * 50 random non-degenerate points in a 100-by-100 grid
  * 5 random non-degenerate points in a 10-by-10 grid
==> passed

Test 6b: Insert n distinct points and call nearest() with random query points
  * 50000 random distinct points in a 100000-by-100000 grid
  * 10000 random distinct points in a 10000-by-10000 grid
  * 10000 random distinct points in a 1000-by-1000 grid
  * 5000 random distinct points in a 100-by-100 grid
  * 50 random distinct points in a 10-by-10 grid
==> passed

Test 6c: Insert n general points and call nearest() with random query points
  * 10000 random general points in a 1000-by-1000 grid
  * 10000 random general points in a 100-by-100 grid
  * 10000 random general points in a 10-by-10 grid
==> passed

Test 7a: Intermixed sequence of calls to insert(), isEmpty(), size(),
         contains(), range(), and nearest() with probabilities
         (p1, p2, p3, p4, p5, p6), respectively
  * 20000 calls with non-degenerate points in a 100000-by-100000 grid
     and probabilities (0.3, 0.05, 0.05, 0.1, 0.2, 0.2)
  * 20000 calls with non-degenerate points in a 10000-by-10000 grid
     and probabilities (0.3, 0.05, 0.05, 0.1, 0.2, 0.2)
  * 20000 calls with non-degenerate points in a 1000-by-1000 grid
     and probabilities (0.3, 0.05, 0.05, 0.1, 0.2, 0.2)
  * 20000 calls with non-degenerate points in a 100-by-100 grid
     and probabilities (0.3, 0.05, 0.05, 0.1, 0.2, 0.2)
  * 20000 calls with non-degenerate points in a 10-by-10 grid
     and probabilities (0.3, 0.05, 0.05, 0.1, 0.2, 0.2)
  * 20000 calls with non-degenerate points in a 1-by-1 grid
     and probabilities (0.3, 0.05, 0.05, 0.1, 0.2, 0.2)
==> passed

Test 7b: Intermixed sequence of calls to insert(), isEmpty(), size(),
         contains(), range(), and nearest() with probabilities
         (p1, p2, p3, p4, p5, p6), respectively
  * 20000 calls with distinct points in a 100000-by-100000 grid
     and probabilities (0.3, 0.05, 0.05, 0.2, 0.2, 0.2)
  * 20000 calls with distinct points in a 10000-by-10000 grid
     and probabilities (0.3, 0.05, 0.05, 0.2, 0.2, 0.2)
  * 20000 calls with distinct points in a 1000-by-1000 grid
     and probabilities (0.3, 0.05, 0.05, 0.2, 0.2, 0.2)
  * 20000 calls with distinct points in a 100-by-100 grid
     and probabilities (0.3, 0.05, 0.05, 0.2, 0.2, 0.2)
  * 20000 calls with distinct points in a 10-by-10 grid
     and probabilities (0.3, 0.05, 0.05, 0.2, 0.2, 0.2)
  * 20000 calls with distinct points in a 1-by-1 grid
     and probabilities (0.3, 0.05, 0.05, 0.2, 0.2, 0.2)
==> passed

Test 7c: Intermixed sequence of calls to insert(), isEmpty(), size(),
         contains(), range(), and nearest() with probabilities
         (p1, p2, p3, p4, p5, p6), respectively
  * 20000 calls with general points in a 100000-by-100000 grid
     and probabilities (0.3, 0.05, 0.05, 0.2, 0.2, 0.2)
  * 20000 calls with general points in a 10000-by-10000 grid
     and probabilities (0.3, 0.05, 0.05, 0.2, 0.2, 0.2)
  * 20000 calls with general points in a 1000-by-1000 grid
     and probabilities (0.3, 0.05, 0.05, 0.2, 0.2, 0.2)
  * 20000 calls with general points in a 100-by-100 grid
     and probabilities (0.3, 0.05, 0.05, 0.2, 0.2, 0.2)
  * 20000 calls with general points in a 10-by-10 grid
     and probabilities (0.3, 0.05, 0.05, 0.2, 0.2, 0.2)
  * 20000 calls with general points in a 1-by-1 grid
     and probabilities (0.3, 0.05, 0.05, 0.2, 0.2, 0.2)
==> passed

Test 8: Intermixed sequence of calls to insert(), isEmpty(), size(),
        contains(), range(), and nearest() with probabilities
        (p1=0, p2, p3, p4, p5, p6), respectively
        (data structure with 0 points)
  * 1000 calls with no points in a 1000-by-1000 grid
     and probabilities (0.0, 0.5, 0.5, 0.0, 0.0, 0.0)
  * 1000 calls with no points in a 1000-by-1000 grid
     and probabilities (0.0, 0.2, 0.2, 0.6, 0.0, 0.0)
  * 1000 calls with no points in a 1000-by-1000 grid
     and probabilities (0.0, 0.2, 0.2, 0.0, 0.6, 0.0)
  * 1000 calls with no points in a 1000-by-1000 grid
     and probabilities (0.0, 0.2, 0.2, 0.0, 0.0, 0.6)
  * 1000 calls with no points in a 1000-by-1000 grid
     and probabilities (0.0, 0.2, 0.2, 0.2, 0.2, 0.2)
==> passed


Total: 19/19 tests passed!


================================================================
********************************************************************************
*  MEMORY
********************************************************************************

Computing memory of Point2D
*-----------------------------------------------------------
Memory of Point2D object = 32 bytes
================================================================



Computing memory of RectHV
*-----------------------------------------------------------
Memory of RectHV object = 48 bytes
================================================================



Computing memory of KdTree
*-----------------------------------------------------------
Running 8 total tests.

Memory usage of a KdTree with n points (including Point2D and RectHV objects).
Maximum allowed memory is 312n + 192 bytes.

                 n       student (bytes)    reference (bytes)
--------------------------------------------------------------
=> passed        1          160                160
=> passed        2          288                288
=> passed        5          672                672
=> passed       10         1312               1312
=> passed       25         3232               3232
=> passed      100        12832              12832
=> passed      400        51232              51232
=> passed      800       102432             102432
==> 8/8 tests passed

Total: 8/8 tests passed!

Estimated student   memory (bytes) = 128.00 n + 32.00  (R^2 = 1.000)
Estimated reference memory (bytes) = 128.00 n + 32.00  (R^2 = 1.000)
================================================================



********************************************************************************
*  TIMING                                                                  
********************************************************************************

Timing PointSET
*-----------------------------------------------------------
Running 14 total tests.


Inserting n points into a PointSET

               n      ops per second
----------------------------------------
=> passed   160000    1699869         
=> passed   320000    1746834         
=> passed   640000    1372048         
=> passed  1280000    1132615         
==> 4/4 tests passed

Performing contains() queries after inserting n points into a PointSET

               n      ops per second
----------------------------------------
=> passed   160000     500811         
=> passed   320000     481670         
=> passed   640000     418744         
=> passed  1280000     442781         
==> 4/4 tests passed

Performing range() queries after inserting n points into a PointSET

               n      ops per second
----------------------------------------
=> passed    10000       4114         
=> passed    20000       1971         
=> passed    40000        893         
==> 3/3 tests passed

Performing nearest() queries after inserting n points into a PointSET

               n      ops per second
----------------------------------------
=> passed    10000       4871         
=> passed    20000       2233         
=> passed    40000        902         
==> 3/3 tests passed

Total: 14/14 tests passed!


================================================================



Timing KdTree
*-----------------------------------------------------------
Running 28 total tests.


Inserting n points into a 2d tree. The table gives the average number of calls to methods
in RectHV and Point per call to insert().

                                                                                                Point2D
               n      ops per second       RectHV()           x()               y()             equals()
----------------------------------------------------------------------------------------------------------------
=> passed   160000     749204               2.0              46.8              46.8               0.0         
=> passed   320000     780621               2.0              47.6              47.6               0.0         
=> passed   640000     637073               2.0              50.6              50.6               0.0         
=> passed  1280000     463097               2.0              54.8              54.8               0.0         
==> 4/4 tests passed

Performing contains() queries after inserting n points into a 2d tree. The table gives
the average number of calls to methods in RectHV and Point per call to contain().

                                                                               Point2D
               n      ops per second       x()               y()               equals()
-----------------------------------------------------------------------------------------------
=> passed    10000     491976              38.0              38.0               0.0         
=> passed    20000     510041              40.3              40.3               0.0         
=> passed    40000     494144              44.6              44.6               0.0         
=> passed    80000     465479              45.0              45.0               0.0         
=> passed   160000     433224              47.5              47.5               0.0         
=> passed   320000     391932              51.1              51.1               0.0         
=> passed   640000     345699              52.4              52.4               0.0         
=> passed  1280000     295380              55.4              55.4               0.0         
==> 8/8 tests passed

Performing range() queries after inserting n points into a 2d tree. The table gives
the average number of calls to methods in RectHV and Point per call to range().

               n      ops per second       intersects()      contains()        x()               y()
---------------------------------------------------------------------------------------------------------------
=> passed    10000     352849              50.4              31.1              50.1              12.1         
=> passed    20000     341557              52.7              32.6              53.3              16.2         
=> passed    40000     308233              64.9              39.3              63.1              14.1         
=> passed    80000     286788              67.1              40.7              65.2              14.9         
=> passed   160000     234454              70.0              42.5              70.9              20.4         
=> passed   320000     189857              67.0              40.2              65.2              15.7         
=> passed   640000     151636              72.0              43.3              70.7              19.2         
=> passed  1280000     169987              78.7              47.0              74.8              14.2         
==> 8/8 tests passed

Performing nearest() queries after inserting n points into a 2d tree. The table gives
the average number of calls to methods in RectHV and Point per call to nearest().

                                         Point2D                 RectHV
               n      ops per second     distanceSquaredTo()     distanceSquaredTo()        x()               y()
------------------------------------------------------------------------------------------------------------------------
=> passed    10000   323454                  67.0                   19.4                    69.6              68.8         
=> passed    20000   303700                  73.9                   21.6                    76.7              76.1         
=> passed    40000   305638                  87.4                   25.6                    91.7              89.9         
=> passed    80000   291829                  89.3                   26.3                    92.5              92.5         
=> passed   160000   219052                  97.2                   28.7                   101.5             101.1         
=> passed   320000   181704                 101.5                   30.1                   106.3             104.8         
=> passed   640000   146633                 105.4                   31.2                   109.6             108.9         
=> passed  1280000   156520                 118.3                   35.2                   122.9             122.2         
==> 8/8 tests passed

Total: 28/28 tests passed!


================================================================
