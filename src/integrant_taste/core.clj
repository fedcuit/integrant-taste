(ns integrant-taste.core
  (:require [clojure.edn :as edn]
            [ring.adapter.jetty :refer [run-jetty]]
            [ring.util.response :refer [response]]
            [ring.middleware.defaults :refer [wrap-defaults api-defaults]]
            [ring.middleware.json :refer [wrap-json-response wrap-json-body]]
            [compojure.core :refer :all]
            [compojure.route :as route]
            [com.walmartlabs.lacinia :refer [execute]]
            [com.walmartlabs.lacinia.util :refer [attach-resolvers]]
            [com.walmartlabs.lacinia.schema :as ls])
  (:gen-class))

(def resolvers
  {:get-hero  (constantly {})
   :get-droid (constantly {})})

(def app-schema
  (-> "resources/schema.edn"
      slurp
      edn/read-string
      (attach-resolvers resolvers)
      ls/compile))

(defroutes graphql
  (context "/graphql" []
    (GET "/" [] "GraphiQL")
    (POST "/" {{:keys [query variables]} :body}
      (response (execute app-schema query variables nil)))))

(defroutes app
  (GET "/" [] "Hello Compojure")
  graphql
  (route/not-found "Page Not Found"))

(def handler
  (-> app
      (wrap-json-body {:keywords? true})
      wrap-json-response
      (wrap-defaults api-defaults)))

(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (run-jetty handler {:port 8000}))