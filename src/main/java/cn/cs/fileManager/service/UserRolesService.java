package cn.cs.fileManager.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import cn.cs.fileManager.dao.mapper.FmUserRoleMapper;
import cn.cs.fileManager.dao.model.FmUser;
import cn.cs.fileManager.dao.model.FmUserRole;
import cn.cs.fileManager.dao.model.FmUserRoleExample;
import cn.cs.fileManager.dao.model.FmUserRoleExample.Criteria;

@Transactional(value = "transactionManager", rollbackFor = Exception.class, isolation = Isolation.READ_COMMITTED, timeout = 300)
@Service
public class UserRolesService implements IUserRoleService {
	@Autowired
	private IUserService userService;
	@Autowired
	private FmUserRoleMapper fmUserRoleMapper;
	
	 @Value("${custom.AdminRole}")
	    private  long adminRole;
	 
	 @Value("${custom.NormalRole}")
	    private  long normalRole;

	@Override
	public boolean updateRoles(FmUser record) {
		try {
			FmUser u=this.userService.getInfoOf(record.getId());
			List<String> userRoles=u.getRoles();
			List<String> recordRoles=record.getRoles();
			for(int i=0;i<recordRoles.size();i++)
			{
				String item=recordRoles.get(i);
				if(userRoles.contains(item)) {
				}else {
					FmUserRole role=new FmUserRole();
					role.setUserId(record.getId());
					
					if(item.equalsIgnoreCase("admin"))
					{
						role.setRoleId(adminRole);
					}else {
						role.setRoleId(normalRole);
					}
					role.setUserRoleStatus("1");
					fmUserRoleMapper.insert(role);
				}			
			}
			
			for(int i=0;i<userRoles.size();i++)
			{
				String item=userRoles.get(i);
				if(!recordRoles.contains(item)) {
					FmUserRoleExample fe=new FmUserRoleExample();
					Criteria criteria = fe.createCriteria();
					criteria.andUserIdEqualTo(record.getId());
					if(item.equalsIgnoreCase("admin"))
					{
						criteria.andRoleIdEqualTo(adminRole);
					}else {
						criteria.andRoleIdEqualTo(normalRole);
					}
					fmUserRoleMapper.deleteByExample(fe);
				}		
			}
		} catch (Exception e) {
			return false;
		}
		return true;
	}

	@Override
	public boolean addUserRole(FmUser fmUser) {
		try {
			List<String> recordRoles=fmUser.getRoles();
			for(int i=0;i<recordRoles.size();i++)
			{
				String item=recordRoles.get(i);
				FmUserRole role=new FmUserRole();
				role.setUserId(fmUser.getId());
				
				if(item.equalsIgnoreCase("admin"))
				{
					role.setRoleId(adminRole);
				}
				if(item.equalsIgnoreCase("user"))
				{
					role.setRoleId(normalRole);
				}
				role.setUserRoleStatus("1");
				fmUserRoleMapper.insert(role);
			}
		} catch (Exception e) {
			return false;
		}
		return true;
	}
	
}
