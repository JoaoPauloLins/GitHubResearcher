


# GitHubResearcher
Um app de pesquisa de repositórios do GitHub.

## Sobre o app
- Neste aplicativo, que usando o GitHub REST API v3 como base, você pode pesquisar por repositórios públicos do GitHub, assim como criar listas de repositórios e ter acesso rápido a informações de seus repositórios também.
- [Vídeo demonstrando principais funcionalidades](https://youtu.be/K8KaUbvNuMY)
- [API made by us that communicates with Github REST API v3](https://github.com/matheusraz/requisicaoAPIGitJS)

## Especificações
- O app foi escrito em Java e para este projeto seguimos os guidelines de desenvolvimento da Google, e por isso utilizamos a arquitetura MVVM, assim como as bibliotecas: Room para persistência de dados, Retrofit2 para consumo de API REST, Dagger2 para injeção de dependências, entre outras. A lista completa de dependências do projeto pode ser visto no [build.gradle](https://github.com/JoaoPauloLins/IF1001-GithubResearcher/blob/master/app/build.gradle).

## Arquitetura

![arquiteturaghr](https://user-images.githubusercontent.com/20371104/42635917-b95c2c9a-85bd-11e8-8d68-7eb5608bb584.PNG)

 1. VO contém as entidades do projeto. As entidades que possuem a anotação @Entity são as que são persistidas no banco.
 
![voghr](https://user-images.githubusercontent.com/20371104/42635960-e399c9e0-85bd-11e8-805f-7306ed3c79be.PNG)

- A maioria das entidades estão descritas como é mostrado abaixo:

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

 - A entidade Resource recebe uma entidade genérica e é utilizada para setar um estado sobre está entidade quando é feita uma requisição REST. Por exemplo: quando o usuário faz uma pesquisa de um repositório, é retornado um objeto *LiveData<Resource<List< Repo>>>* na qual é possível verificar se ele está em estado de *loading* (quando a requisição ainda está em andamento), se está em *success* (quando a requisição foi feita com sucesso e retornou o objeto preenchido com os dados da requisição) ou *error* (quando a requisição retornou alguma mensagem de erro).

		public class Resource <T> {  
		  
		    @NonNull  
		  public final Status status;  
		  
		    @Nullable  
		  public final String message;  
		  
		    @Nullable  
		  public final T data;  
		  
		    public Resource(@NonNull Status status, @Nullable T data, @Nullable String message) {  
		        this.status = status;  
		        this.data = data;  
		        this.message = message;  
		    }  
		  
		    public static <T> Resource<T> success(@Nullable T data) {  
		        return new Resource<>(SUCCESS, data, null);  
		    }  
		  
		    public static <T> Resource<T> error(String msg, @Nullable T data) {  
		        return new Resource<>(ERROR, data, msg);  
		    }  
		  
		    public static <T> Resource<T> loading(@Nullable T data) {  
		        return new Resource<>(LOADING, data, null);  
		    }  
		  
		    @Override  
		  public boolean equals(Object o) {  
		        if (this == o) {  
		            return true;  
		        }  
		        if (o == null || getClass() != o.getClass()) {  
		            return false;  
		        }  
		  
		        Resource<?> resource = (Resource<?>) o;  
		  
		        if (status != resource.status) {  
		            return false;  
		        }  
		        if (message != null ? !message.equals(resource.message) : resource.message != null) {  
		            return false;  
		        }  
		        return data != null ? data.equals(resource.data) : resource.data == null;  
		    }  
		  
		    @Override  
		  public int hashCode() {  
		        int result = status.hashCode();  
		        result = 31 * result + (message != null ? message.hashCode() : 0);  
		        result = 31 * result + (data != null ? data.hashCode() : 0);  
		        return result;  
		    }  
		  
		    @Override  
		  public String toString() {  
		        return "Resource{" +  
		                "status=" + status +  
		                ", message='" + message + '\'' +  
		                ", data=" + data +  
		                '}';  
		    }  
		}
 
 2. API contém as classes que lidam com as requisições a API REST:
 ![apighr](https://user-images.githubusercontent.com/20371104/42636444-37d30106-85bf-11e8-834d-9cc702acc8b8.PNG)
- A classe ApiResponse é responsável por tratar o retorno das requisições REST, informando se a requisição foi feita com sucesso ou não.
- A interface/classe AuthenticationHeader é responsável por setar e retornar um header para a requisição a ser feita.
- A interface GithubService contém todos os métodos que fazem as requisições REST e retornam objetos LiveData<ApiResponse< T >>

		public interface GithubService {  
		  
		    @GET("autentica")  
		    LiveData<ApiResponse<User>> getUser();  
		  
		    @GET("repos/{login}")  
		    LiveData<ApiResponse<List<Repo>>> getRepos(@Path("login") String login);  
		  
		    @GET("lerReadme/{login}/{repoName}")  
		    LiveData<ApiResponse<Readme>> getRepoReadme(@Path("login") String login,  
		                                                @Path("repoName") String repoName);  
		  
		    @GET("search/repos/{query}/{page}/{perPage}")  
		    LiveData<ApiResponse<List<Repo>>> getSearchRepos(@Path("query") String query,  
		                                                     @Path("page") int page,  
		                                                     @Path("perPage") int perPage);  
		}
 
 3. DB contém a classe criação do banco de dados, assim como as interfaces DAO:

![dbghr](https://user-images.githubusercontent.com/20371104/42637052-9369f19a-85c0-11e8-8939-cf757383fd7a.PNG)


	@Database(entities = {User.class, Repo.class, UserList.class, RepoList.class}, version = 1)  
	public abstract class GithubDb extends RoomDatabase {  
	  
	    abstract public UserDao userDao();  
	    abstract public RepoDao repoDao();  
	    abstract public UserListDao userListDao();  
	    abstract public RepoListDao repoListDao();  
	}

- Exemplo de uma interface Dao:

		@Dao  
		public interface UserListDao {  
		  
		    @Insert(onConflict = OnConflictStrategy.REPLACE)  
		    void insertUserList(UserList userList);  
		  
		    @Query("SELECT * FROM userList WHERE userId = :userId")  
		    LiveData<List<UserList>> findUserListByUserId(int userId);  
		  
		    @Query("SELECT * FROM userList WHERE userId = :userId AND name = :name")  
		    LiveData<List<UserList>> findUserListByName(int userId, String name);  
		  
		    @Query("SELECT * FROM userList WHERE id = :id")  
		    UserList findUserListById(int id);  
		  
		    @Query("SELECT u.id FROM userList u WHERE NOT EXISTS (SELECT * FROM repoList WHERE listId = u.id AND repoId = :repoId)")  
		    List<Integer> findUserListIdByRepoId(int repoId);  
		}

4. Repository contém as classes que consomem a api/db  e retornam um LiveData para os ViewsModel:
![repositoryghr](https://user-images.githubusercontent.com/20371104/42637271-1eb88e5a-85c1-11e8-824e-6d19c5dc82f9.PNG)

- No exemplo abaixo vemos que no método loadRepos, é feito um requisição ao banco para achar os repositórios de determinado usuário e caso ache, é setado no 
*MediatorLiveData<Resource<List< Repo>>> result* o valor *Resource.success(List< Repo>)*.
- Caso contrário, é setado o valor  *Resource.loading(null)* e logo após faz a requisição a api. Caso o status da resposta da api seja Successful, é feita a inserção dos repositórios no banco (utilizando o appExecutors.diskIO().execute() para realizar as operações de banco fora da MainThread), e depois é setado o valor *Resource.success(List< Repo>)*. Caso contrário é setado o valor *Resource.error(null)*.
- Por fim é retornado o valor final do result. O mesmo processo é feito nas demais classes de Repository.

		public class RepoRepositoryImpl implements RepoRepository {  
		  
		    private final AppExecutors appExecutors;  
		    private final RepoDao repoDao;  
		    private final GithubService githubService;  
		  
		    private final MediatorLiveData<Resource<List<Repo>>> result = new MediatorLiveData<>(); 
		  
		    public RepoRepositoryImpl(AppExecutors appExecutors,  
		                              RepoDao repoDao,  
		                              GithubService githubService) {  
		        this.appExecutors = appExecutors;  
		        this.repoDao = repoDao;  
		        this.githubService = githubService;  
		    }  
		  
		    @MainThread  
		  private void setValue(Resource<List<Repo>> newValue) {  
		        if (!Objects.equals(result.getValue(), newValue)) {  
		            result.setValue(newValue);  
		        }  
		    }
		  
		    @Override  
		  public LiveData<Resource<List<Repo>>> loadRepos(String user) {  
		  
		        int userId = Integer.parseInt(user.split(":")[0]);  
		        String userLogin = user.split(":")[1];  
		  
		        LiveData<List<Repo>> dbSource = repoDao.findReposByUserId(userId);  
		        result.addSource(dbSource, data -> {  
		            assert data != null;  
		            if (data.size() == 0) {  
		                result.removeSource(dbSource);  
		                LiveData<ApiResponse<List<Repo>>> apiResponse = githubService.getRepos(userLogin);  
		                result.addSource(dbSource, newData ->setValue(Resource.loading(newData)));  
		                result.addSource(apiResponse, response -> {  
		                    result.removeSource(apiResponse);  
		                    result.removeSource(dbSource);  
		                    //noinspection ConstantConditions  
		  if (response.isSuccessful()) {  
		                        appExecutors.diskIO().execute(() -> {  
		                            List<Repo> repos = response.body;  
		                            assert repos != null;  
		                            for (Repo repo : repos) {  
		                                repoDao.insertRepos(repo);  
		                            }  
		                            result.addSource(repoDao.findReposByUserId(userId),  
		                                    newData -> setValue(Resource.success(repos)));  
		                        });  
		                    } else {  
		                        result.addSource(dbSource,  
		                                newData -> setValue(Resource.error(response.errorMessage, newData)));  
		                    }  
		                });  
		            } else {  
		                setValue(Resource.success(data));  
		            }  
		        });  
		        return result;  
		    }  
		}
5. Ui contém as classes ViewModel, Fragments e Activities:
![uighr](https://user-images.githubusercontent.com/20371104/42639765-3286187a-85c7-11e8-9dab-088b078f9bfe.PNG)

- A classe LoginViewModel por exemplo, assim como todos os outros ViewModels, recebe as requisições dos Fragments e por sua vez faz a requisição dos dados para algum Repository. Ao receber o retorno do Repository, o ViewModel retorna para o Fragment, que exibe os dados na tela.

		public class LoginViewModel extends ViewModel {  
		  
		    private UserRepository userRepository;  
		  
		    private MutableLiveData<String> authenticationLiveData = new MutableLiveData<>();  
		  
		    private LiveData<Resource<User>> user = Transformations.switchMap(  
		            authenticationLiveData, authentication -> userRepository.loadUser(authentication)  
		    );  
		  
		    @SuppressWarnings("unchecked")  
		    @Inject  
		  public LoginViewModel(UserRepository userRepository) {  
		        this.userRepository = userRepository;  
		    }  
		  
		    public void authenticate(String username, String password) {  
		        authenticationLiveData.setValue(username+":"+password);  
		    }  
		  
		    public LiveData<Resource<User>> getUser() {  
		        return user;  
		    }  
		}
6. ViewModel contém apenas a classe GithubViewModelFactory que é responsável por construir os diferentes tipos de ViewModels e provê-los aos Fragments:
 ![viewmodelghr](https://user-images.githubusercontent.com/20371104/42640539-e6ef9bb4-85c8-11e8-8222-76a5a53629e8.PNG)

		public class GithubViewModelFactory implements ViewModelProvider.Factory {  
		    private final Map<Class<? extends ViewModel>, Provider<ViewModel>> creators;  
		  
		    public GithubViewModelFactory(Map<Class<? extends ViewModel>, Provider<ViewModel>> creators) {  
		        this.creators = creators;  
		    }  
		  
		    @SuppressWarnings("unchecked")  
		    @Override  
		  public <T extends ViewModel> T create(Class<T> modelClass) {  
		        Provider<? extends ViewModel> creator = creators.get(modelClass);  
		        if (creator == null) {  
		            for (Map.Entry<Class<? extends ViewModel>, Provider<ViewModel>> entry : creators.entrySet()) {  
		                if (modelClass.isAssignableFrom(entry.getKey())) {  
		                    creator = entry.getValue();  
		                    break;  
		                }  
		            }  
		        }  
		        if (creator == null) {  
		            throw new IllegalArgumentException("unknown model class " + modelClass);  
		        }  
		        try {  
		            return (T) creator.get();  
		        } catch (Exception e) {  
		            throw new RuntimeException(e);  
		        }  
		    }  
		}
 
7. Di ou Dependency Injection contém as classes/interfaces responsável por prover instâncias únicas de várias interfaces e injetá-los em diferentes partes da aplicação:
![dighr](https://user-images.githubusercontent.com/20371104/42640672-315df89e-85c9-11e8-86ec-f429344cf262.PNG)

		@Module(includes = ViewModelModule.class)  
		public class AppModule {  
		  
		    @Singleton  
		 @Provides  ViewModelProvider.Factory provideViewModelProviderFactory(Map<Class<? extends ViewModel>, Provider<ViewModel>> creators) {  
		        return new GithubViewModelFactory(creators);  
		    }  
		  
		    @Singleton  
		 @Provides  AppExecutors provideAppExecutors() {  
		        return new AppExecutorsImpl(Executors.newSingleThreadExecutor(),  
		                Executors.newFixedThreadPool(3),  
		                new AppExecutorsImpl.MainThreadExecutor());  
		    }  
		  
		    @Singleton  
		 @Provides  AuthenticationHeader provideAuthenticationHeader() {  
		        return new AuthenticationHeaderImpl();  
		    }  
		  
		    @Singleton  
		 @Provides  OkHttpClient provideOkHttpClient(AuthenticationHeader authenticationHeader) {  
		        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();  
		        httpClient.addInterceptor(chain -> {  
		            Request original = chain.request();  
		  
		            Request request = original.newBuilder()  
		                    .header("User", authenticationHeader.getAuthentication())  
		                    .method(original.method(),original.body())  
		                    .build();  
		  
		            return chain.proceed(request);  
		        });  
		  
		        return httpClient.build();  
		    }  
		  
		    @Singleton  
		 @Provides  GithubService provideGithubService(OkHttpClient client) {  
		  
		        return new Retrofit.Builder()  
		                .baseUrl("http://git-researcher-api.herokuapp.com/")  
		                .addConverterFactory(GsonConverterFactory.create())  
		                .addCallAdapterFactory(new LiveDataCallAdapterFactory())  
		                .client(client)  
		                .build()  
		                .create(GithubService.class);  
		    }  
		  
		    @Singleton  
		 @Provides  GithubDb provideDb(Application app) {  
		        return Room.databaseBuilder(app, GithubDb.class,"github.db").build();  
		    }  
		  
		    @Singleton  
		 @Provides  UserDao provideUserDao(GithubDb db) {  
		        return db.userDao();  
		    }  
		  
		    @Singleton  
		 @Provides  RepoDao provideRepoDao(GithubDb db) {  
		        return db.repoDao();  
		    }  
		  
		    @Singleton  
		 @Provides  UserListDao provideUserListDao(GithubDb db) {  
		        return db.userListDao();  
		    }  
		  
		    @Singleton  
		 @Provides  RepoListDao provideRepoListDao(GithubDb db) {  
		        return db.repoListDao();  
		    }  
		  
		    @Singleton  
		 @Provides  UserRepository provideUserRepository(AppExecutors appExecutors,  
		                                         UserDao userDao,  
		                                         GithubService githubService,  
		                                         AuthenticationHeader authenticationHeader) {  
		        return new UserRepositoryImpl(appExecutors, userDao, githubService, authenticationHeader);  
		    }  
		  
		    @Singleton  
		 @Provides  RepoRepository provideRepoRepository(AppExecutors appExecutors,  
		                                         RepoDao repoDao,  
		                                         GithubService githubService) {  
		        return new RepoRepositoryImpl(appExecutors, repoDao, githubService);  
		    }  
		  
		    @Singleton  
		 @Provides  SearchRepository provideSearchRepository(GithubService githubService) {  
		        return new SearchRepositoryImpl(githubService);  
		    }  
		  
		    @Singleton  
		 @Provides  UserListRepository provideUserListRepository(AppExecutors appExecutors,  
		                                                 UserListDao userListDao) {  
		        return new UserListRepositoryImpl(appExecutors, userListDao);  
		    }  
		  
		    @Singleton  
		 @Provides  RepoListRepository provideRepoListRepository(AppExecutors appExecutors,  
		                                                 RepoListDao repoListDao) {  
		        return new RepoListRepositoryImpl(appExecutors, repoListDao);  
		    }  
		}

## Contribuidores
- [joaopaulolins](https://github.com/JoaoPauloLins)
- [matheusraz](https://github.com/matheusraz)
