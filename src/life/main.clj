(ns life.main
  (:require [life.core :as core]
            [clojure.set :as set]))

(defn ->point [[x y]] (life.Point. x y))

(defn ->state [state]
  (life.UIState.
    (:speed state)
    (map ->point (:world state)))) 2

(defn update! [c]
  (.update (.ui c) (->state @(.state c))))

(deftype Controller [ui state]
  life.Controller
  (pause [this] (.speed this 0))

  (reset [this]
    (swap! state assoc :speed 0)
    (swap! state assoc :world #{})
    (update! this))

  (speed [this s]
    (swap! state assoc :speed s)
    (update! this))

  (toggle [this x y]
    (if (contains? (:world @state) [x y])
      (swap! state update-in [:world] set/difference #{[x y]})
      (swap! state update-in [:world] conj [x y]))
    (update! this))
  )
(def default-state {:speed 0 :world #{}})

(defn new-controller [ui world]
  (new Controller ui (atom (assoc default-state :world world))))

(defn start [controller]
  (loop [state @(.state controller)]
    (if (zero? (:speed state))
      (do
        (Thread/sleep 1000))
      (let [w (core/tick (:world state))]
        (swap! (.state controller) assoc :world w)
        (update! controller)
        (let [s (:speed state)
              delay (* 50 (Math/pow 1.5 (- 10 s)))]
          (Thread/sleep delay))))
    (recur @(.state controller))))

(defn -main []
  (let [ui (life.UI.)
        world #{}
        controller (new-controller ui world)]
    (.init ui controller)
    (update! controller)
    (.show ui)
    (start controller)))
