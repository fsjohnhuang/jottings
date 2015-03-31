(ns user)
(declare n m)
(defn f [n]
  (if (zero? n)
    1
    (- n (m (f (dec n))))))
(defn m [n]
  (if (zero? n)
    0
    (- n (f (m (dec n))))))

