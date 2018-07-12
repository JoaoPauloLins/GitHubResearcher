
# GitHubResearcher
Um app de pesquisa de repositórios do GitHub.

## Sobre o app
- Neste aplicativo, que usando o GitHub REST API v3 como base, você pode pesquisar por repositórios públicos do GitHub, assim como criar listas de repositórios e ter acesso rápido a informações de seus repositórios também.
- [API made by us that communicates with Github REST API v3](https://github.com/matheusraz/requisicaoAPIGitJS)

## Especificações
- O app foi escrito em Java e para este projeto seguimos os guidelines de desenvolvimento da Google, e por isso utilizamos a arquitetura MVVM, assim como as bibliotecas: Room para persistência de dados, Retrofit2 para consumo de API REST, Dagger2 para injeção de dependências, entre outras. A lista completa de dependências do projeto pode ser visto no [build.gradle](https://github.com/JoaoPauloLins/IF1001-GithubResearcher/blob/master/app/build.gradle).

## Arquitetura
![Na arquitetura do projeto mostrado acima temos a seguinte configuração:](https://picasaweb.google.com/108200939936964762789/6577310980537677841#6577310982772179106 "ArquiteturaGHR")

 1. VO contém as entidades do projeto
![enter image description here](https://picasaweb.google.com/108200939936964762789/6577313612286749025#6577313613824207826 "voGHR")

    @Entity(primaryKeys = "id")  
    public class User implements Serializable {  

        @SerializedName("id")  
        public final int id;  
        @SerializedName("login")  
        public final String login;  
        @SerializedName("name")  
        public final String name;  
        @SerializedName("email")  
        public final String email;  
        @SerializedName("avatar")  
        public final String avatar;  
        @SerializedName("bio")  
        public final String bio;  

        public User(int id, String login, String name, String email, String avatar, String bio) {  
            this.id = id;  
            this.login = login;  
            this.name = name;  
            this.email = email;  
            this.avatar = avatar;  
            this.bio = bio;  
        }  
    }

 
 2. List item

## Contribuidores
- [joaopaulolins](https://github.com/JoaoPauloLins)
- [matheusraz](https://github.com/matheusraz)
