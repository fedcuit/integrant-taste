(defproject integrant-taste "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "EPL-2.0 OR GPL-2.0-or-later WITH Classpath-exception-2.0"
            :url  "https://www.eclipse.org/legal/epl-2.0/"}
  :dependencies [
                 [org.clojure/clojure "1.9.0"]
                 [com.walmartlabs/lacinia "0.21.0"]
                 [ring/ring-core "1.6.3"]
                 [ring/ring-jetty-adapter "1.6.3"]
                 [ring/ring-json "0.4.0"]
                 [ring/ring-defaults "0.3.2"]
                 [compojure "1.6.1"]
                 ]
  :plugins [[lein-cljfmt "0.6.4"]]
  :main ^:skip-aot integrant-taste.core
  :target-path "target/%s"
  :profiles {:uberjar {:aot :all}})