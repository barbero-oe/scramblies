(ns scramblies-front.events
  (:require
    [re-frame.core :as re-frame]
    [day8.re-frame.http-fx]
    [ajax.core :as ajax]
    [scramblies-front.db :as db]))

(defn initialize-db [_ _] db/default-db)
(re-frame/reg-event-db ::initialize-db initialize-db)

(defn change-string [db [_ value]] (assoc db :string value))
(re-frame/reg-event-db ::change-string change-string)

(defn change-target [db [_ value]] (assoc db :target value))
(re-frame/reg-event-db ::change-target change-target)

(re-frame/reg-event-fx
  ::query-scrambliness
  (fn [{{:keys [string target]} :db} _]
    {:http-xhrio {:method          :get
                  :uri             (str "/scramble/" string "/" target)
                  :response-format (ajax/json-response-format {:keyboards? true})
                  :on-success      [::show-scramble-result]
                  :on-failure      [::show-error]}}))

(re-frame/reg-event-db
  ::show-error
  (fn [db] (assoc db :error "Oops, try again...")))

(re-frame/reg-event-db
  ::show-scramble-result
  (fn [db [_ result]]
    (assoc db :scrambles (:scramble result))))

