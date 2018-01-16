# XQUERY

## Kazalo
* **[Komentarji](#komenatrji)**
* **[Poizvedbe](#poizvedbe)**
* **[FLWOR Ukazi](#flwor-ukazi)**
* **[Pogojni Stavki](#pogojni-stavki)**
* **[XPath](#xpath)**

## Komentarji

### V Kodi
```xquery
(: Komentar / koda ki ne dela :)

```

### V Rezultatu
```xquery
<!-- Komentar / opis vrednosti -->

```

## Poizvedbe

```xquery
doc("relativna/pot/do/dokumenta.xml")/tag1/tag2[@argument = 'vrednost']/tag3

```

PRIMER:

```xquery
doc("narocilo.xml")/narocilo/postavka[@barva='rjava']
```

> Datoteka z poizvedbo je sharnjena tam, kjer je shranjena datoteka `narocila.xml`

## FLWOR ukazi

### LET
```xquery
let $ime_var := $druga_var/tag1/tag2[@argument z logični pogoj]
let $ime_var := doc("relativna/pot/do/dokumenta.xml")/tag1/tag2[@argument = 'vrednost']/tag3
```

> Novo spremeljivko lahko nastavimo na neko vrednost od druge spemeljivke ali pa na neko poizvedbo, da potem ne pišemo še enkrat dolge poizvedbe.

> Ločni pogoj je lahko v obliki `@argumen > vrednost` ali `@argument == 'vrednost'`

### FOR

Je sestavljen iz 5 delov (v tem vrstnem redu):
* **For** stavka (obvezno)
```xquery
for $loop_var in $array_var
```

* Let (neobvezno)
* Where (neobvezno)

* Order by (neobvezno)
* **Return** (obvezno)

PRIMER:

```xquery
let $narocila := doc("narocilo.xml")/narocilo
for $postavka in $narocila/postavka
where $postavka/@oddelek = 'MEN'
order by $postavka/@kolicina
return $postavka
```

## Pogojni stavki

### Primerjalni operatorji

* **Splošni** -> `=, !=, <, <=, >, >=`
> Primerjajo lahko poljubno elementov
* **Vrednostni** ->`eq, ne, lt, le, gt, ge`
> Primerjajo lahko samo en element z to vrednostjo (drugače vrneno napako)

### IF stavki

Splošna zgradba:
```xquery
if (logicna_vrednost)
then ukaz
else ukaz
```
> Else in oklepaji so **NUJNO** potrebeni

PRIMER:
```xquery
if (($var1/tag1 or ($var2/tag1 = vrednost) and $var3/@uniqval eq 'vrednost')
then <tag> "Hello world!" </tag>
else if (not($var1/tag1))
     then <tag> "NOT!" </tag>
     else <tag> "Error" </tag>
```

### Efektivna logična vrednost
**WARNING**

Če izraz ni logična vrednost se uporabi effektivna logična vrednost.

FALSE:
* 0 / NAN
* prazen niz / niz dolžine 0
* xs:boolean vrednost false

TRUE:
* SICER

## Poizvedovalni prolog

TODO

## Custom funkcije

TODO

## XPath

### Smeri Navigacije

* self:: / .
* parent:: / ..
* **child::** (default)
* descendant-or-self: / //
* descendant
* ancestor, ancestor-or-self (inverze od //)
* following, preceding, following-sibling, preceding-sibling

PRIMERI:

```xquery
doc("Freebase/martial_artist.xml")//martial_artist
```

> Izpisi vse pod sabo (martial_artist) trenutno s sabo

```xquery
doc("Freebase/martial_artist.xml")//martial_artist/id/parent::martial_artist
```

> Izpisi vse martial artiste na mal poseben nacin


### Testiranje Vozlisc

* Ime argumenta : @argument

```xquery
doc("narocilo.xml")/narocilo/postavka/@barva
```

* Celoten element : element() / node()
```xquery
doc("Freebase/martial_artist.xml")//martial_artist/element()
doc("Freebase/martial_artist.xml")//martial_artist/node()
```

* Vrednost med tagi : text()

```xquery
doc("Freebase/martial_artist.xml")//martial_artist/name/text()

```
* Poljubna stvar

```xquery
doc("Freebase/martial_artist.xml")/*/*
doc("narocilo.xml")/narocilo/*/@*
```

### Predikati

#### Oblika

```xquery
doc("relativna/pot/dokument.xml")/pot/do/vozlisca[pogoj_1 or pogoj_2][pogoj_3]..[pogoj_n]
```

#### Možni pogoji

* Obstoj argumenta : `@argumen`
* Primerjava argumenta : `@argument = 'vrednost'` (>,<, !=)
* Vrni n-ti element izmed trenutne selekcije : `[pogoj][n]` ali `/pot/do[n]`
* Pozicija v rezultatu : `position()`
* Zadnji element : `last()`
* Vsebuje podniz v nizu : `contains (niz, podniz)`
* Vse razen tega : `* except(arg_1, arg_2, ..., arg_n)`

### Dodajanje elementov

#### Vhodni dokument

`return $var/ime`

> Nic posebnega zelo omejeno

#### Neposredni konstruktor
```xquery
return <tag> {$var/@attr} {$var/text()} {$var/elements} </tag>
```

> @attr bojo šli v atribute tag, text() bo šel v vsebino tag-a, elements bojo bili podznačke

#### Dinamičen konstruktor

Struktura:

```xquery
element ime_znacke {
  (: Vsebina te znacke :)
  attribut ime_atr {vrednost},
  attribut {$dinamicni_atr} {"vrednost"},
  element ime_pod_znacke {attribut ime {"vsebina"}} {"vsebina"}
}
```


