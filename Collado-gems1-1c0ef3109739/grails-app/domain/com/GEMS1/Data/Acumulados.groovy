package com.GEMS1.Data

class Acumulados {
    Date date
    Date anterior
    float h
    float f
    float e
    int fuenteSitioId
    
    static constraints = {
    }
    static mapping = {
        date defaultValue: "'2015-01-25 05:53:27'"
        date defaultValue: "'2015-01-25 05:53:27'"
        version defaultValue: "0"
        h defaultValue: "0"
        f defaultValue: "0"
        e defaultValue: "0"
        sitioId defaultValue: "1"
    }
}
/*
drop table if exists acumulados;
CREATE TABLE IF NOT EXISTS `acumulados` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `date` date DEFAULT NULL,
  `h` float DEFAULT NULL,
  `f` float DEFAULT NULL,
  `e` float DEFAULT NULL,
  h_anual float default null,
  f_anual float default null,
  e_anual float default null,
  eficiencia float default null,
  reporte varchar(80) default null,
  PRIMARY KEY (`id`)
);
*/
