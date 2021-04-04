(ns scramblies-front.scramble-test
  (:require [cljs.test :refer-macros [deftest testing is]]
            [day8.re-frame.test :as rf-test]
            [re-frame.core :as rf]
            [scramblies-front.events :as e]
            [scramblies-front.subs :as s]))

(defn complete-values []
  (rf/dispatch [::e/initialize-db])
  (rf/dispatch [::e/change-string "value"])
  (rf/dispatch [::e/change-target "value"]))

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
        (is (= "newvalue" @target)))))

  (testing "shows error on call failure"
    (rf-test/run-test-sync
      (rf/reg-fx :http-xhrio (fn [{on-failure :on-failure}]
                               (rf/dispatch on-failure)))
      (complete-values)
      (rf/dispatch [::e/query-scrambliness])
      (let [error (rf/subscribe [::s/error])]
        (is (= "Oops, try again..." @error)))))

  (testing "shows successful result of scramble query"
    (rf-test/run-test-sync
      (rf/reg-fx :http-xhrio (fn [{on-success :on-success}]
                               (rf/dispatch (conj on-success {:scramble true}))))
      (complete-values)
      (rf/dispatch [::e/query-scrambliness])
      (let [result (rf/subscribe [::s/scramble-result])]
        (is (= "Scrambles!" @result)))))

  (testing "shows failure result of scramble query"
    (rf-test/run-test-sync
      (rf/reg-fx :http-xhrio (fn [{on-success :on-success}]
                               (rf/dispatch (conj on-success {:scramble false}))))
      (complete-values)
      (rf/dispatch [::e/query-scrambliness])
      (let [result (rf/subscribe [::s/scramble-result])]
        (is (= "Does not scrambles" @result)))))

  (testing "validates alphabetic string input"
    (rf-test/run-test-sync
      (rf/dispatch [::e/initialize-db])
      (rf/dispatch [::e/change-string "value"])
      (rf/dispatch [::e/change-string "value3"])
      (let [string (rf/subscribe [::s/string])
            error (rf/subscribe [::s/error])]
        (is (= "value" @string))
        (is (= "Only lower-case english letters are allowed." @error)))))

  (testing "validates alphabetic target input"
    (rf-test/run-test-sync
      (rf/dispatch [::e/initialize-db])
      (rf/dispatch [::e/change-target "target"])
      (rf/dispatch [::e/change-target "targetC"])
      (let [target (rf/subscribe [::s/target])
            error (rf/subscribe [::s/error])]
        (is (= "target" @target))
        (is (= "Only lower-case english letters are allowed." @error)))))

  (testing "allows empty inputs"
    (rf-test/run-test-sync
      (rf/dispatch [::e/initialize-db])
      (rf/dispatch [::e/change-target ""])
      (let [target (rf/subscribe [::s/target])
            error (rf/subscribe [::s/error])]
        (is (= "" @target))
        (is (= nil @error)))))

  (testing "clears error on typing"
    (rf-test/run-test-sync
      (rf/dispatch [::e/initialize-db])
      (rf/dispatch [::e/change-string "value"])
      (rf/dispatch [::e/change-string "value "])
      (let [error (rf/subscribe [::s/error])]
        (is (= "Only lower-case english letters are allowed." @error)))
      (rf/dispatch [::e/change-string "valueagain"])
      (let [error (rf/subscribe [::s/error])]
        (is (= nil @error)))))

  (testing "querying scrambling service clears errors"
    (rf-test/run-test-sync
      (complete-values)
      (rf/dispatch [::e/change-string "value "])
      (rf/reg-fx :http-xhrio (fn [{on-success :on-success}]
                               (rf/dispatch (conj on-success {:scramble true}))))
      (rf/dispatch [::e/query-scrambliness])
      (let [error (rf/subscribe [::s/error])]
        (is (= nil @error)))))

  (testing "typing clears result"
    (rf-test/run-test-sync
      (rf/reg-fx :http-xhrio (fn [{on-success :on-success}]
                               (rf/dispatch (conj on-success {:scramble true}))))
      (complete-values)
      (rf/dispatch [::e/query-scrambliness])
      (let [scrambles (rf/subscribe [::s/scrambles])]
        (is (= true @scrambles)))
      (rf/dispatch [::e/change-string "value"])
      (let [scramble-result (rf/subscribe [::s/scramble-result])]
        (is (= nil @scramble-result)))))

  (testing "the user needs to enter text"
    (rf-test/run-test-sync
      (rf/dispatch [::e/initialize-db])
      (rf/dispatch [::e/query-scrambliness])
      (let [error (rf/subscribe [::s/error])]
        (is (= "You need to complete the values." @error)))))

  (testing "defines error background color"
    (rf-test/run-test-sync
      (rf/dispatch [::e/initialize-db])
      (rf/dispatch [::e/query-scrambliness])
      (let [color (rf/subscribe [::s/background-color])]
        (is (= "salmon" @color)))))

  (testing "defines normal background color"
    (rf-test/run-test-sync
      (rf/dispatch [::e/initialize-db])
      (let [color (rf/subscribe [::s/background-color])]
        (is (= "transparent" @color)))))

  (testing "defines background color when the string scrambles"
    (rf-test/run-test-sync
      (rf/dispatch [::e/initialize-db])
      (rf/reg-fx :http-xhrio (fn [{on-success :on-success}]
                               (rf/dispatch (conj on-success {:scramble true}))))
      (complete-values)
      (rf/dispatch [::e/query-scrambliness])
      (let [color (rf/subscribe [::s/background-color])]
        (is (= "lightgreen" @color)))))

  (testing "defines background color when the string does not scrambles"
    (rf-test/run-test-sync
      (rf/dispatch [::e/initialize-db])
      (rf/reg-fx :http-xhrio (fn [{on-failure :on-failure}]
                               (rf/dispatch on-failure)))
      (complete-values)
      (rf/dispatch [::e/query-scrambliness])
      (let [color (rf/subscribe [::s/background-color])]
        (is (= "salmon" @color))))))
