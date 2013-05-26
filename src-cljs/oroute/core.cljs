(ns oroute.core
  (:require-macros [oroute.js :as m])
  (:require [clojure.browser.repl :as repl]))

(repl/connect "http://localhost:9000/repl")

(defn enable [elem-name] 
  (let [elem (js/$ elem-name)]
    (fn [_] (.removeAttr elem "disabled"))))

(.ready 
 (js/$ js/document) 
 (fn []
   (m/log "Document ready")
   (.hide (js/$ "#image"))
   (let [image-file (js/$ "#imageFile")
         image-changes (.asEventStream image-file "change")
         has-image (.toProperty (.map image-changes identity))]
     (.onValue has-image (enable "#gpx"))
     (.onValue has-image oroute.imgload/handle-file))))
;;     (.onValue has-image
;;          (fn [_] oroute.imgload/draw-route)))))
