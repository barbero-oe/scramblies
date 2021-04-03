(ns scramblies-front.events
  (:require
    [re-frame.core :as re-frame]
    [day8.re-frame.http-fx]
    [ajax.core :as ajax]
    [scramblies-front.db :as db]))

(defn initialize-db [_ _] db/default-db)
(re-frame/reg-event-db ::initialize-db initialize-db)

(defn alpha? [value] (re-matches #"[a-z]+" value))

(defn validate [predicate error key value]
  (if (predicate value)
    {key value :error nil}
    {:error error}))

(def validate-alpha
  (partial validate alpha? "Only lower-case english letters are allowed."))

(re-frame/reg-event-db
  ::change-string
  (fn [db [_ value]]
    (merge db (validate-alpha :string value)
           {:scrambles nil})))

(re-frame/reg-event-db
  ::change-target
  (fn [db [_ value]]
    (merge db (validate-alpha :target value)
           {:scrambles nil})))

(re-frame/reg-event-fx
  ::query-scrambliness
  (fn [{db :db} _]
    (let [{:keys [string target]} db]
      {:db         (assoc db :error nil)
       :http-xhrio {:method          :get
                    :uri             (str "http://localhost:3000/scramble/" string "/" target)
                    :response-format (ajax/json-response-format {:keywords? true})
                    :on-success      [::show-scramble-result]
                    :on-failure      [::show-error]}})))

(re-frame/reg-event-db
  ::show-error
  (fn [db] (assoc db :error "Oops, try again...")))

(re-frame/reg-event-db
  ::show-scramble-result
  (fn [db [_ result]]
    (assoc db :scrambles (:scramble result))))
