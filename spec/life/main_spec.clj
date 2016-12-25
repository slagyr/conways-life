(ns life.main-spec
  (:require [life.main :refer :all]
            [speclj.core :refer :all]))

(deftype MockUI [state]
  life.LifeUI
  (update [this s] (reset! state s)))

(defn new-mockui []
  (new MockUI (atom {}))
  )

(describe "Main"

  (with ui (new-mockui))
  (with controller (new-controller @ui #{}))

  (it "creates controller"
    (should-not-be-nil @controller)
    (should-be-same @ui (.ui @controller))
    (should-not-be-nil (.state @controller)))

  (it "pause sets speed to zero"
    (.pause @controller)
    (should= 0 (.speed @(.state @ui))))

  (it "sets speed"
    (.speed @controller 7)
    (should= 7 (.speed @(.state @ui))))

  (it "resets"
    (swap! (.state @controller) assoc :world #{[0 0]})
    (.reset @controller)
    (should= 0 (.speed @(.state @ui)))
    (should= 0 (count (.living @(.state @ui)))))

  (it "toggles"
    (.toggle @controller 5 5)
    (should= 1 (count (.living @(.state @ui))))
    (should= (life.Point. 5 5) (first (.living @(.state @ui))))

    (.toggle @controller 5 5)
    (should= 0 (count (.living @(.state @ui)))))

  )


