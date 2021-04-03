(ns scramblies-front.scramble-test
  (:require [cljs.test :refer-macros [deftest testing is]]
            [day8.re-frame.test :as rf-test]
            [re-frame.core :as rf]
            [scramblies-front.events :as e]
            [scramblies-front.subs :as s]))

(deftest changes-db-state
  (testing "changes string value"
    (rf-test/run-test-sync
      (rf/dispatch [::e/initialize-db])
      (rf/dispatch [::e/change-string "newvalue"])
      (let [string (rf/subscribe [::s/string])]
        (is (= "newvalue" @string)))))

  (testing "changes target value"
    (rf-test/run-test-sync
      (rf/dispatch [::e/initialize-db])
      (rf/dispatch [::e/change-target "newvalue"])
      (let [target (rf/subscribe [::s/target])]
        (is (= "newvalue" @target))))))