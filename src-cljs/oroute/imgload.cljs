(ns oroute.imgload
	(:use-macros
 		[dommy.macros :only [sel sel1]]))


(defn drawRoute[]
	(let [canvas (.getElementById js/document "canvas")
	  	  ctx (.getContext canvas "2d")]
	  	  
	 	(set! (.-fillStyle ctx) "#F0F0F0")
	 	(set! (.-strokeStyle ctx) "#F0F0F0")
	 	(.log js/console "Drawing a route" ctx)
	 	(.beginPath ctx)
	 	(.moveTo ctx 10 10)
	 	(.lineTo ctx 1000 1000)
	 	(.fill ctx)
	 	(.stroke ctx)))

(defn handleFile[e]
	(.log js/console "handle file called" e)
	(let [canvas (.getElementById js/document "canvas")
	  	  ctx (.getContext canvas "2d")
	  	  reader (js/FileReader.)]
	  	(set! (.-globalAlpha ctx) 0.5)
   		(set! (.-onload reader) (fn [event]
    		(.log js/console "File loaded" event)
    		(.load (js/$ "#image") (fn []
    			(this-as image
    				(.log js/console "Load image" image) 
					(set! (.-width canvas) (.-width image))
					(set! (.-height canvas) (.-height image))
					(.drawImage ctx image 0 0 (.-width canvas) (.-height canvas)))
    				(drawRoute)))
			(.attr (js/$ "#image") "src" (aget reader "result"))))
	    (let [target (aget e "target")
    		  files (aget target "files")
    		  fst (aget files 0)]
    		(.readAsDataURL reader fst))))