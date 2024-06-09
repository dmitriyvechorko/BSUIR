from function import Function
import calculated_minimization
import mc_cluskey_minimization
import karno_minimization

expr_sdnf = "((((((!x1)^(!x2))^x3)v(((!x1)^x2)^(!x3)))v(((!x1)^x2)^x3))v((x1^x2)^(!x3)))"
expr_scnf = "(((((x1vx2)vx3)^(((!x1)vx2)vx3))^(((!x1)vx2)v(!x3)))^(((!x1)v(!x2))v(!x3)))"

print("{}\nTables of expression".format("#"*70))
print("-SDNF")
print(Function(expr_sdnf).table)
print('{}\n-SCNF'.format("="*40))
print(Function(expr_scnf).table)

print("{}\nCalculated minimization:".format("#"*70))
print(f"-SDNF:{expr_sdnf}\nResult:{calculated_minimization.make_calculated_minimization(expr_sdnf)}")
print("="*40)
print(f"-SCNF:{expr_scnf}\nResult:{calculated_minimization.make_calculated_minimization(expr_scnf)}")

print("{}\nQuine Mc-Cluskey minimization result".format("#"*70))
sdnf_mc_cluskey_str, sdnf_table_view_mc_cluskey = mc_cluskey_minimization.quine_mc_cluskey_result_and_table(expr_sdnf)
print(f"-SDNF:{expr_sdnf}\n{sdnf_table_view_mc_cluskey}\n"
      f"Result:{calculated_minimization.make_calculated_minimization(sdnf_mc_cluskey_str)}")
print("="*40)
scnf_mc_cluskey_str, scnf_table_view_mc_cluskey = mc_cluskey_minimization.quine_mc_cluskey_result_and_table(expr_scnf)
print(f"-SCNF:{expr_scnf}\n{scnf_table_view_mc_cluskey}\n"
      f"Result:{calculated_minimization.make_calculated_minimization(scnf_mc_cluskey_str)}")

print("{}\nKarno minimization result for sdnf".format("#"*70))
karno_sdnf_res_dict = karno_minimization.make_karno_minimization(expr_sdnf)
print(f"-Result DNF:{karno_sdnf_res_dict['dnf'][0]}\n{karno_sdnf_res_dict['dnf'][1]}\n" 
      f"-Result CNF:{karno_sdnf_res_dict['cnf'][0]}\n{karno_sdnf_res_dict['cnf'][1]}\n")
print("="*40)
print("{}\nKarno minimization result for scnf".format("#"*70))
karno_scnf_res_dict = karno_minimization.make_karno_minimization(expr_scnf)
print(f"-Dnf result:{karno_scnf_res_dict['dnf'][0]}\n{karno_scnf_res_dict['dnf'][1]}\n"
      f"-Cnf result:{karno_scnf_res_dict['cnf'][0]}\n{karno_scnf_res_dict['cnf'][1]}\n")
