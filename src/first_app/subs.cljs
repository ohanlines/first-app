(ns first-app.subs
  (:require
   [re-frame.core :as re-frame]))

(re-frame/reg-sub
 ::name
 (fn [db]
   (:name db)))

(re-frame/reg-sub
 ::text-subs
 (fn [db [_ key]]
   (get db key)))

(re-frame/reg-sub
 ::list-subs
 (fn [db]
   (get db :persistent [])))