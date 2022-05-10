(ns first-app.server
  (:require [io.pedestal.http :as http]))

(defn respond [request]
  {:status 200
   :body   "request-123"})

(def routes
  #{["/greet" :get `respond]})

(def service-map
  {::http/routes routes
   ::http/port   8080
   ::http/type   :jetty})

(defonce server (atom nil))

(defn start []
  (->> (assoc service-map ::http/join? false)
       http/create-server
       http/start
       (reset! server)))

(defn stop []
  (http/stop @server))

(defn restart []
  (stop)
  (start))