
Advanced Clover
======

Refine from the old [Magic Clover](https://www.minecraftforum.net/forums/mapping-and-modding-java-edition/minecraft-mods/1291695-magic-clover-four-leaf-clover-in-minecraft) mod by konwboj. Enjoy good ol' days with good ol' mods!

Participant in [TeaCon2026](https://www.teacon.cn/2026).

Usage
====
The `Magic Clover` item accepts the following components:

```
advanced_clover:magic_clover[

    advanced_clover:ingredient_namespace={
        namespace:"ae2"
    },
    
    advanced_clover:entity_list={
        chance:300,
        entity_list:["minecraft:ender_pearl"]
    },
    advanced_clover:item_list={
        item_list:["minecraft:stone","minecraft:dirt","minecraft:diamond","minecraft:iron_ingot"]
    }
]
```
* `ingredient_namespace.namespace` – Accepts a string representing the mod ID of the ingredient used to craft the Magic Clover. This component acts as the random pool index.  
If null, the clover will randomly throw any item, ignoring any whitelist/blacklist in config.


* `entity_list` – Determines which entity(s) will be summoned by the Magic Clover. Default is null, which falls back to the configuration files (where the default is `[minecraft:creeper]` with a `10 (1%)` chance).
  * `entity_list.chance` – Accepts an integer from 0 to 1000. The value is divided by 10 to get the percentage chance that the item summons an entity.
  Example: `100` = `10%` chance.
  * `entity_type.entity_type` – Accepts a list of strings(List\<String\>). This should be the ID of the possible summoned entities.


* `item_list.item_list` – Accepts a list of strings (List\<String\>). This overrides the whitelist/blacklist from the configuration and serves as the item’s own unique whitelist.
Default is null.  
If not null and more than one string has a valid correspounding item, it ignores `ingredient_namespace.namespace`. 