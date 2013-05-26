(ns oroute.imgload
  (:require [goog.dom.xml :as googxml])
  (:require-macros [oroute.js :as m]))

(defn draw-route [points]
  (let [canvas (.getElementById js/document "canvas")
        ctx (.getContext canvas "2d")]
    (set! (.-fillStyle ctx) "#F0F0F0")
    (set! (.-strokeStyle ctx) "#F0F0F0")
    (m/log "Drawing a route" ctx)
    (.beginPath ctx)
    (.moveTo ctx 10 10)
    (.lineTo ctx 1000 1000)
    (.fill ctx)
    (.stroke ctx)))

(defn to-point [trkpt]
  (let [lon (googxml/selectNode "[@lon]")]
    (m/log lon)))

(defn parse-gpx [data]
  (let [gpx (googxml/loadXml data)
        track (googxml/selectNodes gpx "/gpx/trk/trkseg/trkpt")
        ;;track-points (googxml/selectNodes track "trkpt")
        ]
    (m/log gpx "\n" track)
    (map to-point track)))

(defn handle-gpx-file [bus e]
  (m/log "handle gpx file called" e)
  (let [reader (js/FileReader.)
        elem (js/$ "#gpx")]
    (set! (.-onload reader) 
          (fn [event]
            (let [result (aget (aget event "target") "result")]
              (let [points (parse-gpx result)]
                (draw-route points)))
              (.push bus {:type "gpxloaded"})))
    (let [target (aget e "target")
          files (aget target "files")
          fst (aget files 0)]
      (m/log (.readAsText reader fst)))))


(defn handle-image-file [bus e]
  (m/log "handle file called" e)
  (let [reader (js/FileReader.)]
    (set! (.-onload reader) 
          (fn [event]
            (m/log "File loaded" event)
            (.load (js/$ "#image") 
             (fn []
               (this-as image
                        (let [canvas (.getElementById js/document "canvas")
                              ctx (.getContext canvas "2d")]
                          (m/log "Load image" image)
                          (set! (.-globalAlpha ctx) 0.5)
                          (set! (.-width canvas) (.-width image))
                          (set! (.-height canvas) (.-height image))
                          (.drawImage ctx image 0 0 (.-width canvas) (.-height canvas))
                          (.push bus {:type "imageloaded"})))))
            (.attr (js/$ "#image") "src" (aget reader "result"))))
    (let [target (aget e "target")
          files (aget target "files")
          fst (aget files 0)]
      (.readAsDataURL reader fst))))
