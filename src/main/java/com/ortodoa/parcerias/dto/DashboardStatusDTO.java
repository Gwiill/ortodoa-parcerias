
package com.ortodoa.parcerias.dto;

public class DashboardStatusDTO {

    private long aprovados;
    private long reprovados;
    private long pendentes;

    public DashboardStatusDTO(long aprovados, long reprovados, long pendentes) {
        this.aprovados = aprovados;
        this.reprovados = reprovados;
        this.pendentes = pendentes;
    }

    public long getAprovados() { return aprovados; }
    public long getReprovados() { return reprovados; }
    public long getPendentes() { return pendentes; }
    public long getTotal() { return aprovados + reprovados + pendentes; }
}
