(defproject oroute "0.0.1"
  :description "oroute"
  :source-paths ["src-clj"]
  :dependencies [[org.clojure/clojure "1.5.1"]
                 [compojure "1.0.4"]]
  :plugins [[lein-cljsbuild "0.3.2"]
            [lein-ring "0.7.0"]]
  :cljsbuild {
    :repl-listen-port 9000
    :builds [{:source-paths ["src-cljs"]
              :compiler {:output-to "resources/public/js/r.js"
                         :optimizations :whitespace
                         :pretty-print true}}]}
  :ring {:handler oroute.routes/app})
