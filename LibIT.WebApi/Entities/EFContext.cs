﻿using Microsoft.AspNetCore.Identity;
using Microsoft.AspNetCore.Identity.EntityFrameworkCore;
using Microsoft.EntityFrameworkCore;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace LibIT.WebApi.Entities
{
    public class EFContext : IdentityDbContext<DbUser, DbRole, long, IdentityUserClaim<long>,
       DbUserRole, IdentityUserLogin<long>,
       IdentityRoleClaim<long>, IdentityUserToken<long>>
    {
        public virtual DbSet<UserProfile> UserProfile { get; set; }
        public virtual DbSet<Category> Categories { get; set; }

        public EFContext(DbContextOptions<EFContext> options) : base(options) { }
        protected override void OnModelCreating(ModelBuilder builder)
        {
            base.OnModelCreating(builder);

            builder.Entity<DbUserRole>(userRole =>
            {
                userRole.HasKey(ur => new { ur.UserId, ur.RoleId });

                userRole.HasOne(ur => ur.Role)
                    .WithMany(r => r.UserRoles)
                    .HasForeignKey(ur => ur.RoleId)
                    .IsRequired();

                userRole.HasOne(ur => ur.User)
                    .WithMany(r => r.UserRoles)
                    .HasForeignKey(ur => ur.UserId)
                    .IsRequired();
            });
        }
    }
}
