(ns first-app.events
  (:require
   [re-frame.core :as re-frame]
   [first-app.db :as db]))

(re-frame/reg-event-db
 ::initialize-db
 (fn [_ _]
   db/default-db))

(re-frame/reg-event-db
 ::update-text
 (fn [db [_ key val]]
   (assoc db key val)))

(re-frame/reg-event-db
 ::insert-text
 (fn [db]
   (let [data    (get db :text-input)
         text    (get db :text [])
         updated (conj text data)]
     (-> db
         (assoc :text updated)
         (dissoc :text-input)))))