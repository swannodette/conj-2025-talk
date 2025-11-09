(ns conj-2025.core)

(defn adapt-method [[name sig & body]]
  `[~(str name)
    (cljs.core/js-obj
      "configurable" true
      "value"       (fn ~(into [] (drop 1 sig))
                      (cljs.core/this-as ~(first sig)
                        ~@body)))])

(defmacro defel [name fields & methods]
  (let [self (gensym "self")]
    `(do
       (defn ~name [~@fields]
         (let [~self (.construct ~'js/Reflect ~'js/HTMLElement (cljs.core/array) ~name)]
           ~@(map
               (fn [field]
                 `(set! (. ~self ~(symbol (str "-" field))) ~field))
               fields)
           ~self))
       (set! (. ~name ~'-prototype)
         (.create js/Object (. ~'js/HTMLElement ~'-prototype)
           (cljs.core/js-obj
             ~@(mapcat adapt-method methods)))))))
