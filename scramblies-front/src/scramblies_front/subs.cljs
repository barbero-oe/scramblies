(ns scramblies-front.subs
  (:require
    [re-frame.core :as re-frame]))

(re-frame/reg-sub ::string #(:string %))
(re-frame/reg-sub ::target #(:target %))
(re-frame/reg-sub ::scrambles #(:scrambles %))
(re-frame/reg-sub ::error #(:error %))

(re-frame/reg-sub
  ::background-color
  :<- [::scrambles]
  :<- [::error]
  (fn [[scrambles error] _]
    (cond (every? nil? [error scrambles]) "transparent"
          scrambles "lightgreen"
          :else "salmon")))

(re-frame/reg-sub
  ::scramble-result
  :<- [::scrambles]
  (fn [scrambles _]
    (case scrambles
      true "Scrambles!"
      false "Does not scrambles"
      nil)))
