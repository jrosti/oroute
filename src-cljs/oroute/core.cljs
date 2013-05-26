(ns oroute.core
  (:require-macros [oroute.js :as m])
  (:require [clojure.browser.repl :as repl]))

(repl/connect "http://localhost:9000/repl")

(.ready 
 (m/jquery js/document) 
 (fn []
   (m/log "Document ready")
   (.hide (m/jquery "#image"))
   (.change (m/jquery "#file") oroute.imgload/handle-file)))
