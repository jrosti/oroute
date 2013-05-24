(ns oroute.core
	(:require [clojure.browser.repl :as repl]))

(repl/connect "http://localhost:9000/repl")

(.ready (js/$ js/document) (fn [] 
	(.log js/console "Document ready")
	(.hide (js/$ "#image"))
	(.change (js/$ "#file") oroute.imgload/handleFile)))