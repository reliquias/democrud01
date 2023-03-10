package com.example.democrud01.bean;

public class BeanOptions {
	
	private Long id;
	private String conteudo;
	
	public BeanOptions() {
		super();
	}

	public BeanOptions(Long id, String conteudo) {
		super();
		this.id = id;
		this.conteudo = conteudo;
	}

	public String getConteudo() {
		return conteudo;
	}

	public void setConteudo(String conteudo) {
		this.conteudo = conteudo;
	}

	
    @Override
	public String toString() {
		return conteudo;
	}

	@Override
    public boolean equals(Object object) {
        // check for self comparision
        if (this == object) {
            return true;
        }
        if (object == null) {
            return false;
        }
        if (!(object instanceof BeanOptions)) {
            return false;
        }

        BeanOptions that = (BeanOptions) object;

        if (conteudo == null) {
            if (that.getConteudo() != null) {
                return false;
            }
        } else if (!conteudo.equals(that.getConteudo())) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {

        int SEED = 17;

        SEED = 37 * SEED + ((conteudo == null) ? 0 : conteudo.hashCode());

        return SEED;
    }

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
}