package FordFulkerson;

import java.io.*;
import java.util.*;

public class FordFulkersonAlgorithm {


    static List<NetworkEdge>[] adj = new ArrayList[101];
    static boolean chk[] = new boolean[101];
    static int src, sink;

    public static void main(String args[]) {
        Scanner sc = new Scanner(System.in);
        System.out.println("노드와 간선 갯수 입력");
        int v = sc.nextInt();
        int e = sc.nextInt();
        for (int i = 1; i <= v; i++) {
            adj[i] = new ArrayList<NetworkEdge>();
        }
        System.out.println("소스와 싱크 입력");
        src = sc.nextInt();
        sink = sc.nextInt();

        for (int i = 0; i < e; i++) {
            System.out.println("f(u,v)=x // ex)f(1,2)=1 -> 121//");
            int temp = sc.nextInt();
            int from = temp/100;
            int to = (temp - (from * 100)) / 10;
            int capacity = (temp - (from * 100) - (to * 10));
            System.out.println("f("+from+","+to+")="+capacity);
            adj[from].add(new NetworkEdge(to, capacity, 0, adj[to].size()));
            adj[to].add(new NetworkEdge(from, 0, 0, adj[from].size() - 1));
        }

        int ans = 0, added_flow;
        while (0 < (added_flow = find_path(src, 101))) { //한번돌릴때마다 flow가 갱신되며 상태가 달라지므로 find_path가 0 나올때까지 계속 돌리며 찾음
            ans += added_flow;
            for (int i = 1; i <= v; i++) { //다시 돌리기위한 노드방문여부 초기화
                chk[i] = false;
            }
        }

        System.out.println(ans);
    }

    public static int find_path(int cur, int addible_flow) {// O((V+E)F)
        if (cur == sink) return addible_flow; //목적지 도착하면 추가로흘릴수있는flow 리턴
        chk[cur] = true; //현재노드 방문했음을 check

        for (int i = 0; i < adj[cur].size(); i++) { //adj[cur].size()만큼 loop
            NetworkEdge edge = adj[cur].get(i); //edge에 차례대로 가져오며
            if (chk[edge.to] || edge.capacity - edge.flow == 0)
                continue; //연결된 노드가 이미 방문했거나 or flow/capacity가 꽉차있으면 못가므로 pass

            int added = find_path(edge.to, Math.min(addible_flow, edge.capacity - edge.flow)); //갈수있으므로, 연결된 노드를 방문하자
            if (added > 0) { //방문했으면 갱신해야지
                edge.flow += added;
                adj[edge.to].get(edge.residual_idx).flow -= added;
                return added;
            }
        }
        return 0; //find_path 실패하면 경로가 더이상 존재하지않으므로 0 리턴해 알고리즘 종료
    }

    static class NetworkEdge {
        int to, capacity, flow, residual_idx;

        NetworkEdge() {
        }

        NetworkEdge(int t, int c, int f, int ridx) {
            to = t;
            capacity = c;
            flow = f;
            residual_idx = ridx;
        }
    }
}

