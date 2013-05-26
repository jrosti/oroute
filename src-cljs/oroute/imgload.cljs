(ns oroute.imgload
  (:require-macros [oroute.js :as m]))

(defn draw-route []
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

(defn handle-file [bus e]
  (m/log "handle file called" e)
  (let [reader (js/FileReader.)]
    (set! (.-onload reader) 
          (fn [event]
            (m/log "File loaded" event)
            (.load 
             (js/$ "#image") 
             (fn []
               (this-as 
                image
                (let [canvas (.getElementById js/document "canvas")
                      ctx (.getContext canvas "2d")]
                  (m/log "Load image" image)
                  (set! (.-globalAlpha ctx) 0.5)
                  (set! (.-width canvas) (.-width image))
                  (set! (.-height canvas) (.-height image))
                  (.drawImage ctx image 0 0 (.-width canvas) (.-height canvas))
                  (.push bus {:type "imageloaded" :data image})))))
             (.attr (js/$ "#image") "src" (aget reader "result"))))
    (let [target (aget e "target")
          files (aget target "files")
          fst (aget files 0)]
      (.readAsDataURL reader fst))))
