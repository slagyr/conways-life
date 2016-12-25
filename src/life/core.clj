(ns life.core
  (:require [clojure.set :as set]))

(defn alive? [world coords]
  (contains? world coords))

(defn neighbor-coords [[x y]]
  #{[x (dec y)]
    [(inc x) (dec y)]
    [(inc x) y]
    [(inc x) (inc y)]
    [x (inc y)]
    [(dec x) (inc y)]
    [(dec x) y]
    [(dec x) (dec y)]})

(defn living-neighbors [world coords]
  (set/intersection
    (neighbor-coords coords)
    world))

(defn neighbors [world cell]
  (let [coords (neighbor-coords cell)]
    [(set/intersection coords world)
     (set/difference coords world)]))

(defn kill-underpopulated [tally cell living]
  (if (< living 2)
    (update tally :dead conj cell)
    tally))

(defn kill-overpopulated [tally cell living]
  (if (> living 3)
    (update tally :dead conj cell)
    tally))

(defn update-tally [world tally cell]
  (let [[living dead] (neighbors world cell)
        n (count living)]
    (-> tally
        (kill-underpopulated cell n)
        (kill-overpopulated cell n)
        (update :neighbors set/union dead))))

(defn tally [world]
  (reduce
    (partial update-tally world)
    {:dead #{} :neighbors #{}}
    world))

(defn reproduction [world cells]
  (filter
    #(= 3 (count (living-neighbors world %)))
    cells))

(defn tick [world]
  (let [tally (tally world)]
    (-> world
        (set/difference (:dead tally))
        (set/union (reproduction world (:neighbors tally)))
        set)))

