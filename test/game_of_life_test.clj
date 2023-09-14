(ns game-of-life-test
  (:require [clojure.test :refer :all]
            [game_of_life :refer :all]))

(deftest neighbors-of-test
  (is (= #{[1 1] [2 1] [3 1]
           [1 2]       [3 2]
           [1 3] [2 3] [3 3]} (neighbors-of [2 2])))
  (is (= #{[-3 -3] [-2 -3] [-1 -3]
           [-3 -2]         [-1 -2]
           [-3 -1] [-2 -1] [-1 -1]} (neighbors-of [-2 -2]))))

;; [[2 3] [3 3]
;;  [1 2] [ x ]
;;  [1 1]       [3 1]]
(deftest living-neighbors-test
  (is (= 5 (living-neighbors [2 2] #{[2 3] [3 3] [1 2] [1 1] [3 1]})))
  (is (= 5 ((living-neighbors #{[2 3] [3 3] [1 2] [1 1] [3 1]}) [2 2]))))

(deftest will-live-test
  (is (= false (will-live? [2 2] (set (neighbors-of [-2 -2])))))
  (is (= false (will-live? [2 2] #{[2 3] [3 3] [1 2] [1 1]})))
  (is (= true  (will-live? [2 2] #{[2 3] [3 3] [1 2]})))
  (is (= false (will-live? [2 2] #{[2 3] [3 3]})))
  (is (= false (will-live? [2 2] #{[2 3]}))))

;; [x] [] [x] [x] ->  [] [] [x] [x]
;; [x] [] [x] [ ]     [] [] [x] [x]
(deftest next-generation-test
  (is (= #{[3 1] [4 1] [3 2] [4 2]} (next-generation (set '([1 1] [1 2] [3 1] [3 2] [4 2]))))))

(deftest board-to-string-test
  (is (= "#-##\n#-#-" (board-to-string #{[1 1] [1 2] [3 1] [3 2] [4 2]}))))

(deftest string-to-board-test
  (is (= #{[1 1] [1 2] [3 1] [3 2] [4 2]} (string-to-board (board-to-string #{[1 1] [1 2] [3 1] [3 2] [4 2]})))))




;; to run:
;; clj -X <file>/<function>

;; to test:
;; clj -X:test