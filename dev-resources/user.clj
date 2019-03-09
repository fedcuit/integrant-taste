(ns user
  (:require
    [integrant-taste.schema :as s]
    [com.walmartlabs.lacinia :as lacinia]
    [com.walmartlabs.lacinia.pedestal :as lp]
    [io.pedestal.http :as http]
    [integrant-taste.system :as system]
    [clojure.java.browse :refer [browse-url]]
    [clojure.tools.namespace.repl :refer [refresh]]
    [clojure.walk :as walk]
    [com.stuartsierra.component :as component])
  (:import (clojure.lang IPersistentMap)))

(defn simplify
  "Converts all ordered maps nested within the map into standard hash maps, and
   sequences into vectors, which makes for easier constants in the tests, and eliminates ordering problems."
  [m]
  (walk/postwalk
    (fn [node]
      (cond
        (instance? IPersistentMap node) (into {} node)
        (seq? node) (vec node)
        :else node))
    m))

(defonce system (system/new-system))

(defn q
  [query-string]
  (-> system
      :schema-provider
      :schme
    (lacinia/execute query-string nil nil)
      simplify))

(defn start
  []
  (alter-var-root #'system component/start-system)
  (browse-url "http://localhost:8888/")
  :started)

(defn stop
  []
  (when system (alter-var-root #'system component/stop-system))
  :stopped)

(defn reload
  []
  (refresh)
  (stop)
  (start)
  )