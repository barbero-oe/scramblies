(ns scramblies-front.views
  (:require
    [re-frame.core :as re-frame]
    [scramblies-front.subs :as subs]
    [scramblies-front.events :as events]
    [stylefy.core :refer [use-style]]
    [scramblies-front.styles :refer [input-style
                                     vertical-form
                                     button-style
                                     message-info
                                     center
                                     main-style]]))


(defn user-input [{:keys [on-change value]}]
  [:input (use-style input-style
                     {:on-change #(on-change (-> % .-target .-value))
                      :value     value})])

(defn scramble-form [{:keys [string
                             target
                             scramble-result
                             on-scramble
                             on-string-change
                             on-target-change]}]
  [:div (use-style vertical-form)
   [user-input {:on-change on-string-change
                :value     string}]
   [user-input {:on-change on-target-change
                :value     target}]
   [:button (use-style button-style {:on-click on-scramble}) "Scramble"]
   [:div (use-style message-info)
    (when scramble-result
      [:span scramble-result])]])

(defn main-panel []
  (let [string (re-frame/subscribe [::subs/string])
        target (re-frame/subscribe [::subs/target])
        scramble-result (re-frame/subscribe [::subs/scramble-result])
        error (re-frame/subscribe [::subs/error])
        color (re-frame/subscribe [::subs/background-color])]
    [:main (use-style (main-style @color))
     [:div (use-style center)
      [scramble-form
       {:string           @string
        :target           @target
        :scramble-result  @scramble-result
        :on-scramble      #(re-frame/dispatch [::events/query-scrambliness])
        :on-string-change #(re-frame/dispatch [::events/change-string %])
        :on-target-change #(re-frame/dispatch [::events/change-target %])}]
      [:div (use-style message-info)
       [:span @error]]]]))
