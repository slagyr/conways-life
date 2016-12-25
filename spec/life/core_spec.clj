(ns life.core-spec
  (:require [speclj.core :refer :all]
            [life.core :refer :all]))

; Minus
;   0 1 2
; 0
; 1 * * *
; 2
(def minus #{[0 1] [1 1] [2 1]})

; Plus
;   0 1 2
; 0   *
; 1 * * *
; 2   *
(def plus #{[1 0] [0 1] [1 1] [2 1] [1 2]})

(describe "Life"

  (it "knows if a cell is alive"
    (should= false (alive? #{} [0 0]))
    (should= true (alive? #{[0 0]} [0 0])))

  (it "calculates neighbors"
    (let [n (neighbor-coords [0 0])]
      (should= 8 (count n))
      (should-contain [0 -1] n)
      (should-contain [1 -1] n)
      (should-contain [1 0] n)
      (should-contain [1 1] n)
      (should-contain [0 1] n)
      (should-contain [-1 1] n)
      (should-contain [-1 0] n)
      (should-contain [-1 -1] n)))

  (it "finds living neighbors"
    (let [world #{[1 -1] [1 1] [-1 0] [5 5]}
          n (living-neighbors world [0 0])]
      (should-contain [1 -1] n)
      (should-contain [1 1] n)
      (should-contain [-1 0] n)))

  (it "underpopulated die"
    (let [world (tick minus)]
      (should= true (set? world))
      (should-not-contain [0 1] world)
      (should-not-contain [2 1] world)))

  (it "overpopulated die"
    (let [world (tick plus)]
      (should= true (set? world))
      (should-not-contain [1 1] world)))

  (it "well populated live"
    (let [world (tick plus)]
      (should= true (set? world))
      (should-contain [1 0] world)
      (should-contain [0 1] world)
      (should-contain [2 1] world)
      (should-contain [1 2] world))
    (let [world (tick minus)]
      (should= true (set? world))
      (should-contain [1 1] world)))

  (it "reporduction"
    (let [world (tick plus)]
      (should= true (set? world))
      (should-contain [0 0] world)
      (should-contain [2 0] world)
      (should-contain [0 2] world)
      (should-contain [2 2] world))
    (let [world (tick minus)]
      (should= true (set? world))
      (should-contain [1 0] world)
      (should-contain [1 2] world)))

  )
