(ns scramblies-front.views-test
  (:require [cljs.test :refer-macros [deftest testing is]]
            [scramblies-front.views :as views]
            [com.rpl.specter :refer [walker NONE ALL] :refer-macros [select select-any]]))

(defn has-text [view text]
  (select-any (walker (partial = text)) view))

(deftest component-view-test
  (testing "shows input parameters"
    (let [string "initialstring"
          target "targetstring"
          view (views/scramble-form {:string string :target target})]
      (is (= string (has-text view string)))
      (is (= target (has-text view target)))))

  (testing "does not show scramble result when not specified"
    (let [not-specified (views/scramble-form {})]
      (is (= NONE (has-text not-specified "Scrambles!")))
      (is (= NONE (has-text not-specified "Does not scrambles")))))

  (testing "shows scramble result"
    (let [scrambles (views/scramble-form {:scramble-result "Scrambles!"})]
      (is (= "Scrambles!" (has-text scrambles "Scrambles!"))))))
