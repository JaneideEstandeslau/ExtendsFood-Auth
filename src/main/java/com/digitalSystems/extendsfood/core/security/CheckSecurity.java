package com.digitalSystems.extendsfood.core.security;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;

public @interface CheckSecurity {

	public @interface Cozinhas {
		
		@PreAuthorize("hasAuthority('SCOPE_WRITE') and hasAuthority('EDITAR_COZINHAS')")
		@Retention(RUNTIME)
		@Target(METHOD)
		public @interface PodeEditar { }

		@PreAuthorize("hasAuthority('SCOPE_READ') and isAuthenticated()")
		@Retention(RUNTIME)
		@Target(METHOD)
		public @interface PodeConsultar { }
		
	}
	
	public @interface Restaurantes {

	    @PreAuthorize("hasAuthority('SCOPE_WRITE') and hasAuthority('EDITAR_RESTAURANTES')")
	    @Retention(RUNTIME)
	    @Target(METHOD)
	    public @interface PodeGerenciarCadastro { }
	    
	    @PreAuthorize("hasAuthority('SCOPE_WRITE') and "
				+ "(hasAuthority('EDITAR_RESTAURANTES') or "
				+ "@extendsSecurity.gerenciaRestaurante(#restauranteId))")
		@Retention(RUNTIME)
		@Target(METHOD)
		public @interface PodeGerenciarFuncionamento { }

	    @PreAuthorize("hasAuthority('SCOPE_READ') and isAuthenticated()")
	    @Retention(RUNTIME)
	    @Target(METHOD)
	    public @interface PodeConsultar { }
	    
	}
	
	public @interface Pedidos {
		
		@PreAuthorize("hasAuthority('SCOPE_READ') and isAuthenticated()")
		@PostAuthorize("hasAuthority('CONSULTAR_PEDIDOS') or "
				+ "@extendsSecurity.usuarioAutenticadoIgual(returnObject.cliente.id) or "
				+ "@extendsSecurity.gerenciaRestaurante(returnObject.restaurante.id)")
		@Retention(RUNTIME)
		@Target(METHOD)
		public @interface PodeBuscar { }
		
		@PreAuthorize("hasAuthority('SCOPE_READ') and (hasAuthority('CONSULTAR_PEDIDOS') or "
				+ "@extendsSecurity.gerenciaRestaurante(#filtro.restauranteId) or "
				+ "@extendsSecurity.usuarioAutenticadoIgual(#filtro.clienteId))")
		@Retention(RUNTIME)
		@Target(METHOD)
		public @interface PodePesquisar {}
		
		@PreAuthorize("@extendsSecurity.podeGerenciarPedidos(#pedidoId)")
		@Retention(RUNTIME)
		@Target(METHOD)
		public @interface PodeGerenciarPedido {}
		
		@PreAuthorize("hasAuthority('SCOPE_WRITE') and isAuthenticated()")
		@Retention(RUNTIME)
		@Target(METHOD)
		public @interface PodeCriar {}
		
	}
	
	public @interface FormasPagamento{
		
		@PreAuthorize("hasAuthority('SCOPE_READ') and isAuthenticated()")
		@Retention(RUNTIME)
		@Target(METHOD)
		public @interface PodeConsultar {}
		
		@PreAuthorize("hasAuthority('SCOPE_WRITE') and hasAuthority('EDITAR_FORMAS_PAGAMENTO')")
		@Retention(RUNTIME)
		@Target(METHOD)
		public @interface PodeEditar {}
	}
	
	public @interface Cidades {
		
		@PreAuthorize("hasAuthority('SCOPE_READ') and isAuthenticated()")
		@Retention(RUNTIME)
		@Target(METHOD)
		public @interface PodeConsultar {}
		
		@PreAuthorize("hasAuthority('SCOPE_WRITE') and hasAuthority('EDITAR_CIDADES')")
		@Retention(RUNTIME)
		@Target(METHOD)
		public @interface PodeEditar {}
	}
	
	public @interface Estados {
		
		@PreAuthorize("hasAuthority('SCOPE_READ') and isAuthenticated()")
		@Retention(RUNTIME)
		@Target(METHOD)
		public @interface PodeConsultar {}
		
		@PreAuthorize("hasAuthority('SCOPE_WRITE') and hasAuthority('EDITAR_ESTADOS')")
		@Retention(RUNTIME)
		@Target(METHOD)
		public @interface PodeEditar {}
	}
	
	public @interface UsuariosGruposPermissoes {
		
		@PreAuthorize("hasAuthority('SCOPE_WRITE') and @extendsSecurity.usuarioAutenticadoIgual(#usuarioId)")
		@Retention(RUNTIME)
		@Target(METHOD)
		public @interface PodeAlterarPropriaSenha {}
		
		@PreAuthorize("hasAuthority('SCOPE_WRITE') and (@extendsSecurity.usuarioAutenticadoIgual(#usuarioId) or "
				+ "hasAuthority('EDITAR_USUARIOS_GRUPOS_PERMISSOES'))")
		@Retention(RUNTIME)
		@Target(METHOD)
		public @interface PodeAlterarUsuario {}
		
		@PreAuthorize("hasAuthority('SCOPE_WRITE') and hasAuthority('EDITAR_USUARIOS_GRUPOS_PERMISSOES')")
		@Retention(RUNTIME)
		@Target(METHOD)
		public @interface PodeEditar {}
		
		@PreAuthorize("hasAuthority('SCOPE_READ') and hasAuthority('CONSULTAR_USUARIOS_GRUPOS_PERMISSOES')")
		@Retention(RUNTIME)
		@Target(METHOD)
		public @interface PodeConsultar {}
		
	}
	
	public @interface Estatisticas {
		
		@PreAuthorize("hasAuthority('SCOPE_READ') and hasAuthority('GERAR_RELATORIOS')")
		@Retention(RUNTIME)
		@Target(METHOD)
		public @interface PodeConsultar {}
	}
	
}