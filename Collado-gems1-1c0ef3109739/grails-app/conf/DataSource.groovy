import org.codehaus.groovy.grails.orm.hibernate.cfg.GrailsAnnotationConfiguration

dataSource {
    pooled = true
    dbCreate = "update"
    //url = "jdbc:mysql://162.243.228.180/gemsbd?autoReconnect=true"    
    url = "jdbc:mysql://mysql2001.shared-servers.com:1091/gemsbd?autoReconnect=true"
    //url = "jdbc:mysql://132.254.39.213/gemsbd?autoReconnect=true"
    //url = "jdbc:mysql://localhost/gemsbd?autoReconnect=true"
    driverClassName = "com.mysql.jdbc.Driver"
    //username = "cedittec"
    //password = "server"
    username = "userScada"
    password = "ScadaGems1"
   
}

hibernate {
    cache.use_second_level_cache = true
    cache.use_query_cache = true
    cache.provider_class = 'net.sf.ehcache.hibernate.EhCacheProvider'
}

// environment specific settings
environments {
    development {
        dataSource {
            dbCreate = "update"
            //url = "jdbc:mysql://162.243.228.180/gemsbd?autoReconnect=true"    
    url = "jdbc:mysql://mysql2001.shared-servers.com:1091/gemsbd?autoReconnect=true"
           // url = "jdbc:mysql://132.254.39.213/gemsbd?autoReconnect=true"
            //url = "jdbc:mysql://localhost/gemsbd?autoReconnect=true"
            username = "userScada"
            password = "ScadaGems1"
        }
    }

    production {
          dataSource {  
            dbCreate = "update"
            //url = "jdbc:mysql://162.243.228.180/gemsbd?autoReconnect=true"
    url = "jdbc:mysql://mysql2001.shared-servers.com:1091/gemsbd?autoReconnect=true"
            //url = "jdbc:mysql://132.254.39.213/gemsbd?autoReconnect=true"
            //url = "jdbc:mysql://localhost/gemsbd?autoReconnect=true"
            username = "userScada"
            password = "ScadaGems1"
        }
        
    }
}