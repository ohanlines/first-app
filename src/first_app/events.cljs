(ns first-app.events
  (:require
   [re-frame.core :as re-frame]
   [akiroz.re-frame.storage :refer [persist-db]]
   [first-app.db :as db]))

(re-frame/reg-event-db
 ::initialize-db
 (fn [_ _]
   db/default-db))

;; persistent app db function
(defn reg-event-persistent-db
  [event-id handler]
  (re-frame/reg-event-fx
   event-id
   [(persist-db :my-db :text)]
   (fn [{:keys [db]} event-vec]
     {:db (handler db event-vec)})))

(re-frame/reg-event-db
 ::update-text
 (fn [db [_ key val]]
   (assoc db key val)))

(reg-event-persistent-db
 ::insert-text
 (fn [db]
   (let [input   (get db :text-staging)
         storage (get db :text [])
         updated (concat storage (vector input))]
     (-> db
         (assoc :text updated)
         (dissoc :text-staging)))))