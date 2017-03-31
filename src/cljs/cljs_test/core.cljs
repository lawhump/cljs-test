;; create the main project namespace
(ns cljs-test.core)

;; enable cljs to print to the JS console of the browser
(enable-console-print!)

(def not-nil? (complement nil?))

(defn rmClass [elem className]
  (if (.contains (.-classList elem) className)
      (.remove (.-classList elem) className)
      (println "noop")
      )
    )

(defn route []
  ((let [ destination (.-hash js/location)
          sections { :bio (.querySelector js/document ".bio-wrapper")
                     :caravan (.querySelector js/document ".caravan-wrapper")
                     :tidbits (.querySelector js/document ".tidbits-wrapper")
                     }
          ]
      (cond (= 0 (compare destination "#/projects/caravan")) (.add (.-classList (:caravan sections)) "active")
            (= 0 (compare destination "#/projects/tidbits")) (.add (.-classList (:tidbits sections)) "active")
            (= 0 (compare destination "#/bio")) (.add (.-classList (:bio sections)) "active")
            ; (not-nil? (re-matches #"^$|.#" destination)) (.add (.-classList (:bio sections)) "active")
            ; :else (.log js/console (map? sections))
            :else (reduce-kv (fn [m k v]
                               (assoc m k (rmClass v "active")))
                             {}
                             sections)
            ; :else (reduce #((rmClass % "active"))
            ;               (second sections)
            ;         )
        )
      )
    )
  )

(.addEventListener js/document "DOMContentLoaded" route)

(aset js/window "onhashchange" route)

; (.log js/console (.-hashchange js/window))
