(ns oroute.core
  (:require-macros [oroute.js :as m])
  (:require [clojure.browser.repl :as repl]))

(repl/connect "http://localhost:9000/repl")

(defn enable [elem-name] 
  (let [elem (js/$ elem-name)]
    (fn [_] (.removeAttr elem "disabled"))))

(defn changes [elem]
  (let [change-stream (.asEventStream elem "change")]
    (.toProperty (.map change-stream identity))))

(.ready 
 (js/$ js/document) 
 (fn []
   (m/log "Document ready")
   (.hide (js/$ "#image"))
   (let [image-file (js/$ "#imageFile")
         gpx-file (js/$ "#gpx")
         has-image (changes image-file)
         has-gpx (changes gpx-file)
         bus (js/Bacon.Bus.)
         image-loaded (.toProperty bus)
         ]
     (.onValue has-image (enable "#gpx"))
     (.onValue has-gpx (partial oroute.imgload/handle-gpx-file bus))
     (.onValue has-image (partial oroute.imgload/handle-image-file bus)))))
