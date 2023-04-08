# Fields in `icons.properties`  
* `game.entities` - Все сущности игры, у которых можно поменять вид  
  _Разделитель:_ `,`  
  _Пример:_ `game.entities = character,target,ground,fireball`

* Далее для каждой указанной сущности записывается строка вида  
  `entyty_name.looks = available_looks`  
  _Разделитель:_ `,`  
  _Пример:_ `character.looks = skeleton,dinosaur`  

* Затем для каждого указанного вида указывается путь к файлу с превью для списка выбора облика в настройках  
  `look_name.preview = path_from_src`  
  _Разделитель_: `,`  
  _Пример:_ `skeleton.preview = src/main/resources/assets/Robot/front_stay.png`  

* Путь до стандартного изображения, которое отображается, когда не удаётся загрузить картинку  
  `default.image = path_to_defaul_image_from_src`

* Далее идут записи с расположением картинок для каждого кадра, состояния, вида каждой сущности.
  Можно указать количество кадров в поле `entity.action.count = frames_count`  
  _Примеры:_
  * `target.hart = src/main/resources/assets/Target/heart.png`
  * `skeleton.front.staying = src/main/resources/assets/Robot/front_stay.png`
  * `skeleton.back.moving.count = 4
    skeleton.back.moving.1 = src/main/resources/assets/Robot/back_1.png  
    skeleton.back.moving.2 = src/main/resources/assets/Robot/back_3.png  
    skeleton.back.moving.3 = src/main/resources/assets/Robot/back_1.png  
    skeleton.back.moving.4 = src/main/resources/assets/Robot/back_7.png`
  * `fireball.orange.right.count = 5  
    fireball.orange.right.1 = src/main/resources/assets/Fireball/right_1.png`