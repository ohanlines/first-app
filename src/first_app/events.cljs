(ns first-app.events
  (:require
   [first-app.db :as db]
   [re-frame.core :as re-frame]
   [akiroz.re-frame.storage :refer [persist-db <-store]]
   ))

;; --- initialize storage ---------------------------
(re-frame/reg-event-db
 ::initialize-db
 (fn [_ _]
   {:persistant (<-store :my-db)}))

;; --- persistent reg-event-db function -------------
(defn reg-event-persistent-db
  [event-id handler]
  (re-frame/reg-event-fx
   event-id
   [(persist-db :my-db :text)]
   (fn [{:keys [db]} event-vec]
     {:db (handler db event-vec)})))

;; --- re-frame events ------------------------------
(re-frame/reg-event-db
 ::update-text
 (fn [db [_ key val]]
   (assoc db key val)))

(re-frame/reg-event-db
 ::dissoc-text
 (fn [db]
   (dissoc db :text-staging)))

(reg-event-persistent-db
 ::insert-text
 (fn [db]
   (let [input   (get db :text-staging)
         storage (get db :text [])
         updated (concat storage (vector input))]
     (-> db
         (assoc :text updated)
         (assoc :persistant updated)))))

(reg-event-persistent-db
 ::clear-storage
 (fn [db]
   (-> db
       (assoc :text [])
       (assoc :persistant []))))